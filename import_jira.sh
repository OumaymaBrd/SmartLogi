#!/bin/bash

JIRA_BASE="https://oumaymabramid.atlassian.net"
JIRA_PROJECT="SDMSV01"
JIRA_EMAIL="oumaymabramid@gmail.com"
JIRA_TOKEN="ATATT3xFfGF0KPT7bhpyiF3lcR9zIo2D3E5VmFOFe7jcjMmZ-sBGI9zJeuJI8UL4htiolRCMyrIn64A7Eb5xrKSaMS8PF2xOS9D3t4qHfbpZA8hJXgE60xpheKel-tfk61TPtvAMxAueKiEPw6aTB1n4T_PSdGtoAcgkOUIdd-r1psiQ-pkBAi0=F32F98B1"

TASK_FILE="tasks_clean.txt"

declare -A EPIC_KEYS
declare -A STORY_KEYS

normalize() {
    echo "$1" | \
      sed "y/àáâäãåçèéêëìíîïðñòóôöõùúûüýÿÀÁÂÄÃÅÇÈÉÊËÌÍÎÏÐÑÒÓÔÖÕÙÚÛÜÝ/aaaaaaceeeeiiiidnooooouuuuyyAAAAAACEEEEIIIIDNOOOOOUUUUY/" | \
      tr '[:upper:]' '[:lower:]' | \
      sed -E 's/[^a-z0-9]+/ /g' | \
      sed -E 's/ +/ /g' | \
      xargs
}

extract_field() {
    local line="$1"
    local field="$2"
    echo "$line" | awk -F ' \\| ' -v field="$field" '
      {for (i=1;i<=NF;i++){if($i~"^"field": "){sub("^"field": ","",$i);print $i;exit}}}'
}

create_epic() {
    local summary="$1"
    [ -z "$summary" ] && return
    local temp_json=$(mktemp)
    jq -n --arg summary "$summary" --arg project "$JIRA_PROJECT" '
    {
      fields: {
        project: {key: $project},
        summary: $summary,
        issuetype: {name: "Epic"}
      }
    }' > "$temp_json"
    RESPONSE=$(curl -s -u "$JIRA_EMAIL:$JIRA_TOKEN" -X POST \
        -H "Content-Type: application/json" \
        --data-binary "@$temp_json" \
        "$JIRA_BASE/rest/api/3/issue")
    rm -f "$temp_json"
    KEY=$(echo "$RESPONSE" | jq -r '.key // empty')
    if [ -z "$KEY" ]; then
        echo "❌ Erreur EPIC : $summary"
        echo "$RESPONSE" | jq '.'
    else
        EPIC_KEYS["$(normalize "$summary")"]="$KEY"
        echo "✅ Epic : $summary ($KEY)"
    fi
}

create_story() {
    local summary="$1"
    local epic="$2"
    [ -z "$summary" ] && return
    local story_key
    story_key="$(normalize "${epic}___${summary}")"
    local temp_json=$(mktemp)
    jq -n --arg summary "$summary" --arg project "$JIRA_PROJECT" '
    {
      fields: {
        project: {key: $project},
        summary: $summary,
        issuetype: {name: "Tâche"}
      }
    }' > "$temp_json"
    RESPONSE=$(curl -s -u "$JIRA_EMAIL:$JIRA_TOKEN" -X POST \
        -H "Content-Type: application/json" \
        --data-binary "@$temp_json" \
        "$JIRA_BASE/rest/api/3/issue")
    rm -f "$temp_json"
    KEY=$(echo "$RESPONSE" | jq -r '.key // empty')
    if [ -z "$KEY" ]; then
        echo "❌ Erreur Tâche : $summary"
        echo "$RESPONSE" | jq '.'
    else
        STORY_KEYS["$story_key"]="$KEY"
        echo "✅ Tâche : $summary ($KEY)"
    fi
}

create_subtask() {
    local sub_summary="$1"
    local story_summary="$2"
    local epic_summary="$3"
    local story_key
    story_key="$(normalize "${epic_summary}___${story_summary}")"
    local parent_key="${STORY_KEYS[$story_key]}"
    [ -z "$sub_summary" ] && return
    [ -z "$parent_key" ] && { echo "❌ Pas de tâche parente pour sous-tâche : $sub_summary (clé ${story_key})"; return; }
    local temp_json=$(mktemp)
    jq -n --arg summary "$sub_summary" --arg project "$JIRA_PROJECT" --arg parent "$parent_key" '
    {
      fields: {
        project: {key: $project},
        summary: $summary,
        issuetype: {name: "Sous-tâche"},
        parent: {key: $parent}
      }
    }' > "$temp_json"
    RESPONSE=$(curl -s -u "$JIRA_EMAIL:$JIRA_TOKEN" -X POST \
        -H "Content-Type: application/json" \
        --data-binary "@$temp_json" \
        "$JIRA_BASE/rest/api/3/issue")
    rm -f "$temp_json"
    SUB_KEY=$(echo "$RESPONSE" | jq -r '.key // empty')
    if [ -z "$SUB_KEY" ]; then
        echo "❌ Erreur sous-tâche : $sub_summary"
        echo "$RESPONSE" | jq '.'
    else
        echo "➕ Sous-tâche : $sub_summary ($SUB_KEY)"
    fi
}

echo "Création des Epics..."
grep '^Epic:' "$TASK_FILE" | while read -r line; do
    epic_summary=$(extract_field "$line" "Epic")
    [ -n "$epic_summary" ] && create_epic "$epic_summary"
done

echo "Création des Tâches..."
grep 'User Story:' "$TASK_FILE" | while read -r line; do
    epic_summary=$(extract_field "$line" "Epic")
    story_summary=$(extract_field "$line" "User Story")
    [ -n "$epic_summary" ] && [ -n "$story_summary" ] && create_story "$story_summary" "$epic_summary"
done

echo "Création des Sous-tâches..."
grep 'Sub-task:' "$TASK_FILE" | while read -r line; do
    epic_summary=$(extract_field "$line" "Epic")
    story_summary=$(extract_field "$line" "User Story")
    sub_summary=$(extract_field "$line" "Sub-task")
    [ -n "$epic_summary" ] && [ -n "$story_summary" ] && [ -n "$sub_summary" ] && create_subtask "$sub_summary" "$story_summary" "$epic_summary"
done

echo "✨ Fini !"