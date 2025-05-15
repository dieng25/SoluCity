# Définir la variable
$frontend = "xmart-frontend-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Optionnel : exécuter mvn package (décommenter si besoin)
# mvn package
# if ($LASTEXITCODE -ne 0) {
#     Write-Host "Erreur : échec mvn package"
#     exit 1
# }

# Aller dans le dossier target
Set-Location -Path "xmart-frontend\target"
if ($?) {
    # Exécuter le JAR
    java -jar $frontend
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Erreur : execution $frontend impossible"
        exit 1
    }
} else {
    Write-Host "Erreur : dossier target non trouvé"
    exit 1
}
