#!/bin/bash

#Variable
frontend="xmart-frontend-1.0-SNAPSHOT-jar-with-dependencies.jar"

# mvn package
# if [[ $? -ne 0 ]]; then
# echo "Erreur : échec mvn package" 
# exit 1
# fi

cd "xmart-frontend/target"
if [[ $? -ne 0 ]]; then
echo "Erreur : dossier target non trouvé " 
exit 1
fi

java -jar $frontend
if [[ $? -ne 0 ]]; then
echo "Erreur : execution $frontend impossible" 
exit 1
fi