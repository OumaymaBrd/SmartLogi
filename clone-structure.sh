#!/bin/bash

MAIN_DIR="src/main/java/org/example/smartspring"
TEST_DIR="src/test/java/org/example/smartspring"

# Parcourt tous les fichiers Java du dossier main
find "$MAIN_DIR" -type f -name "*.java" | while read file; do
  # Calcule le chemin relatif
  relative_path="${file#$MAIN_DIR/}"
  
  # Crée le chemin correspondant dans test/
  test_file="$TEST_DIR/${relative_path%.java}Test.java"
  
  # Crée le dossier s’il n’existe pas
  mkdir -p "$(dirname "$test_file")"
  
  # Crée le fichier vide avec le même nom + Test (sans toucher main)
  if [ ! -f "$test_file" ]; then
    touch "$test_file"
    echo "✅ Créé : $test_file"
  fi
done

echo "🎯 Structure de test clonée avec succès !"
