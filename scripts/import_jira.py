#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests
import json
import sys
from typing import Dict, List, Tuple

# -------------------------
# CONFIGURATION
# -------------------------
JIRA_BASE = "https://oumaymabramid.atlassian.net"
JIRA_PROJECT = "SDMSV01"
JIRA_EMAIL = "oumaymabramid@gmail.com"
JIRA_TOKEN = "ATATT3xFfGF0KPT7bhpyiF3lcR9zIo2D3E5VmFOFe7jcjMmZ-sBGI9zJeuJI8UL4htiolRCMyrIn64A7Eb5xrKSaMS8PF2xOS9D3t4qHfbpZA8hJXgE60xpheKel-tfk61TPtvAMxAueKiEPw6aTB1n4T_PSdGtoAcgkOUIdd-r1psiQ-pkBAi0=F32F98B1"

TASK_FILE = "tasks.txt"

# Storage for created keys
epic_keys: Dict[str, str] = {}
story_keys: Dict[str, str] = {}

# -------------------------
# HELPER FUNCTIONS
# -------------------------
def extract_field(line: str, field: str) -> str:
    """Extract a field value from a line formatted as 'Field: Value | Field2: Value2'"""
    parts = line.split(" | ")
    for part in parts:
        if part.strip().startswith(f"{field}: "):
            return part.strip()[len(f"{field}: "):]
    return ""

def create_issue(summary: str, issue_type: str, parent_key: str = None) -> str:
    """Create a Jira issue and return its key"""
    url = f"{JIRA_BASE}/rest/api/3/issue"

    payload = {
        "fields": {
            "project": {"key": JIRA_PROJECT},
            "summary": summary,
            "issuetype": {"name": issue_type}
        }
    }

    if parent_key:
        payload["fields"]["parent"] = {"key": parent_key}

    response = requests.post(
        url,
        auth=(JIRA_EMAIL, JIRA_TOKEN),
        headers={"Content-Type": "application/json"},
        json=payload
    )

    if response.status_code in [200, 201]:
        result = response.json()
        return result.get("key", "")
    else:
        print(f"❌ Erreur création {issue_type}: {summary}")
        print(f"   Status: {response.status_code}")
        print(f"   Response: {response.text}")
        return ""

def parse_tasks_file(filename: str) -> List[Dict]:
    """Parse the tasks.txt file and return a structured list"""
    tasks = []

    with open(filename, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if not line:
                continue

            task = {
                'epic': extract_field(line, 'Epic'),
                'story': extract_field(line, 'User Story'),
                'subtask': extract_field(line, 'Sub-task'),
                'priority': extract_field(line, 'Priority'),
                'duration': extract_field(line, 'Duration')
            }

            tasks.append(task)

    return tasks

# -------------------------
# MAIN IMPORT LOGIC
# -------------------------
def main():
    print("🚀 Début de l'importation des tâches dans Jira...")
    print()

    # Parse tasks file
    tasks = parse_tasks_file(TASK_FILE)

    # PASSE 1: Create all Epics
    print("📋 PASSE 1: Création des Epics...")
    epics = list(set(task['epic'] for task in tasks if task['epic']))

    for epic_name in sorted(epics):
        key = create_issue(epic_name, "Epic")
        if key:
            epic_keys[epic_name] = key
            print(f"✅ Epic créé : {epic_name} ({key})")

    print()

    # PASSE 2: Create all Stories
    print("📋 PASSE 2: Création des Stories...")
    stories = list(set(
        (task['epic'], task['story'])
        for task in tasks
        if task['epic'] and task['story']
    ))

    for epic_name, story_name in sorted(stories):
        parent_key = epic_keys.get(epic_name)

        if not parent_key:
            print(f"⚠️  Epic parent introuvable pour Story : {story_name} (Epic: {epic_name})")
            continue

        key = create_issue(story_name, "Story", parent_key)
        if key:
            story_keys[story_name] = key
            print(f"✅ Story créé : {story_name} ({key})")

    print()

    # PASSE 3: Create all Sub-tasks
    print("📋 PASSE 3: Création des Sub-tasks...")
    subtasks = [
        (task['story'], task['subtask'])
        for task in tasks
        if task['story'] and task['subtask']
    ]

    for story_name, subtask_name in subtasks:
        parent_key = story_keys.get(story_name)

        if not parent_key:
            print(f"⚠️  Story parent introuvable pour Sub-task : {subtask_name} (Story: {story_name})")
            continue

        key = create_issue(subtask_name, "Sub-task", parent_key)
        if key:
            print(f"➕ Sub-task créé : {subtask_name} ({key})")

    print()
    print("✨ Importation terminée !")
    print(f"   {len(epic_keys)} Epics créés")
    print(f"   {len(story_keys)} Stories créés")

if __name__ == "__main__":
    main()
