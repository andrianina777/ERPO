# ERPO

## Auto-déploiement

La branche `master` est automatiquement déployée
vers le serveur de production.

Pour copier les modifications depuis la branche
`backend_prepa` vers master, effectuer les
opérations suivantes:

```
git checkout master
git pull
git checkout backend_prepa
git pull
git rebase -i master
git checkout master
git merge backend_prepa
git push
```

