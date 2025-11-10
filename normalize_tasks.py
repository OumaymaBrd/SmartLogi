# Copie d'abord tout ton tasks.txt d'origine dans ce répertoire !
from collections import defaultdict

# Utilise une forme "normalisée" sans espace/tiret/accent pour matcher chaque (epic,user_story)
import unicodedata, re

def normalize(s):
    s = s.lower()
    s = ''.join(c for c in unicodedata.normalize('NFD', s) if unicodedata.category(c) != 'Mn')
    s = s.replace("-", " ").replace("/", " ")
    s = re.sub(r"\s+", " ", s)
    s = s.strip()
    return s

# charge tous les epic+userstories et leur libellé d'origine
story_map = defaultdict(str)
epic_map = defaultdict(str)
lines = []
with open("tasks.txt", encoding="utf-8") as f:
    for line in f:
        l = line.strip()
        if not l or 'User Story:' not in l: continue
        epic = re.search(r"Epic:\s*([^|]+)", l)
        story = re.search(r"User Story:\s*([^|]+)", l)
        if epic:
            epic_key = normalize(epic.group(1))
            epic_map[epic_key] = epic.group(1).strip()
        if story:
            story_key = normalize(story.group(1))
            story_map[(epic_key, story_key)] = story.group(1).strip()

# maintenant corrige toutes les lignes
with open("tasks.txt", encoding="utf-8") as f, open("tasks_clean.txt", "w", encoding="utf-8") as out:
    for line in f:
        l = line.rstrip('\n')
        epic = re.search(r"Epic:\s*([^|]+)", l)
        story = re.search(r"User Story:\s*([^|]+)", l)
        new_line = l
        if epic:
            # Corrige le nom de l'epic si variant
            epic_norm = normalize(epic.group(1))
            new_line = new_line.replace(epic.group(1), epic_map[epic_norm])
        if story:
            story_norm = normalize(story.group(1))
            e_norm = normalize(re.search(r"Epic:\s*([^|]+)", l).group(1))
            canon_story = story_map.get((e_norm, story_norm), story.group(1).strip())
            new_line = new_line.replace(story.group(1), canon_story)
        out.write(new_line+"\n")