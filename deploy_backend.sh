#!/bin/bash

#Variable
backend="xmart-zity-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"
host_name="solucityback"
ip_adresse="172.31.249.224"


mvn package
if [[ $? -ne 0 ]]; then
	echo "Erreur : échec mvn package" 
	exit 1
fi

cd xmart-city-backend/target
if [[ $? -ne 0 ]]; then
    echo "Erreur : dossier target non trouvé"
    exit 1
fi

scp $backend $host_name@$ip_adresse:backend.jar
if [[ $? -ne 0 ]]; then
    echo "Erreur : échec du transfert $backend vers vm"
    exit 1
else
    echo "Déploiement backend vers vm réussi"
fi
