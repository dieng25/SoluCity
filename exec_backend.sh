#!/bin/bash

#Variable
user_name="solucityback"
host_name="172.31.249.224" #ip vm solucity backend
file="backend.jar"

ssh -t -t $user_name@$host_name <<EOF
    echo "Connexion réussie à la VM : $host_name"
	
    if [[ ! -f "$file" ]]; then
        echo "Erreur : fichier $file introuvable sur la VM"
        exit 1
    fi
	java -jar $file
EOF