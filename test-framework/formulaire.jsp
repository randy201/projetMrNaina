<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire</title>
</head>
<body>
    <form action="Emp-testEmp" method="post" enctype="multipart/form-data">
        <label for="nom">Nom:</label>
        <input type="text"  name="nom" id="nom">

        <label for="prenom">prenom:</label>
        <input type="text"  name="prenom" id="prenom">

        <label for="etu">Etu:</label>
        <input type="text"  name="etu" id="etu">

        <input type="file" name="fichier" id="">

        <input type="submit" value="ok" >
    </form>
</body>
</html>