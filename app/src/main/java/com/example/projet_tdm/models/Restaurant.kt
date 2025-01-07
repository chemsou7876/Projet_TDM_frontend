package com.example.projet_tdm.models

import com.example.projet_tdm.R

data class Restaurant(
    val id:Int,
    val name:String,
    val imgUrl:Int,
    val description:String,
    val typeCuisine:String,
    val localisation:String,
    val noteMoy:Double,
    val nombreComm:Int,
    val deliverytime:String,
    val deliveryprice: String,
    val mapImg:Int,
    val phone:String,
    val openHour: String,
    val closeHour:String,
    val facebook:String,
    val instagram:String,
    val menus:List<Menu>
)


data class Menu(
    val id:Int,
    val name:String,
    val imageUrl:Int,
    val price:Int,
    val description:String,
)


fun getData():List<Restaurant> {
    val data = mutableListOf<Restaurant>()

    data.add(
        Restaurant(
            id = 0,
            name = "Rose Garden Restaurant",
            imgUrl = R.drawable.restaurant_image,
            description = "Burger - Chicken - Wings",
            typeCuisine = "Fast Food",
            localisation = "Alger, Algérie",
            noteMoy = 4.7,
            nombreComm = 250,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.el_dey_map,
            phone = "+213 541234567",
            openHour = "10:00",
            closeHour = "23:00",
            facebook = "rosegarden.dz",
            instagram = "@rosegarden_dz",
            menus = listOf(
                Menu(0, "Classic Burger", R.drawable.burger, 500, "Un burger classique avec un fromage fondant et une sauce savoureuse."),
                Menu(1, "Burger Bistro", R.drawable.burger, 1200, "Burger avec un steak juteux et des légumes frais."),
                Menu(2, "Poulet Grillé", R.drawable.poulet_grille, 1800, "Poulet grillé tendre accompagné d'une garniture savoureuse."),
                Menu(3, "Chicken Wings", R.drawable.chicken_wings, 1500, "Ailes de poulet croustillantes avec une sauce épicée."),
                Menu(4, "Nuggets", R.drawable.nuggets, 800, "Bouchées de poulet dorées, parfaites pour un snack rapide."),
                Menu(5, "Salade Méditerranéenne", R.drawable.salade_mediterraneenne, 1000, "Salade fraîche avec des légumes méditerranéens et une vinaigrette légère.")
            )

        )
    )

    data.add(
        Restaurant(
            id = 2,
            name = "El Djenina",
            imgUrl = R.drawable.eldjenina,
            description = "Restaurant proposant une cuisine algérienne traditionnelle et authentique.",
            typeCuisine = "Algérienne",
            localisation = "Oran, Algérie",
            noteMoy = 5.0,
            nombreComm = 180,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.el_djenina_map,
            phone = "+213 555678912",
            openHour = "11:00",
            closeHour = "22:30",
            facebook = "eldjenina.oran",
            instagram = "@eldjenina_restaurant",
            menus = listOf(
                Menu(6, "Cheese Deluxe", R.drawable.burger2, 650, "Un burger garni de fromage cheddar fondant et de bacon croustillant."),
                Menu(7, "Couscous Royal", R.drawable.couscous_royal, 1500, "Couscous traditionnel avec viandes variées."),
                Menu(8, "Chorba Frik", R.drawable.chorba_frik, 600, "Soupe traditionnelle algérienne à base de frik."),
                Menu(9, "Tajine d'Agneau", R.drawable.tajine_agneau, 2000, "Tajine d'agneau avec légumes et épices."),
                Menu(10, "Bourek à la Viande", R.drawable.bourek_viande, 700, "Bourek croustillant fourré à la viande hachée."),
                Menu(11, "Makroud", R.drawable.makroud, 500, "Pâtisserie traditionnelle à base de semoule et dattes.")
            )
        )
    )
    data.add(
        Restaurant(
            id = 3,
            name = "Sushi Sensei",
            imgUrl = R.drawable.samurai_sushi,
            description = "Restaurant de sushi offrant des plats japonais frais et savoureux.",
            typeCuisine = "Japonaise",
            localisation = "Alger, Algérie",
            noteMoy = 3.7,
            nombreComm = 300,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.sushi_sensei_map,
            phone = "+213 555123789",
            openHour = "12:00",
            closeHour = "23:00",
            facebook = "sushisensei.alger",
            instagram = "@sushisensei_dz",
            menus = listOf(
                Menu(12, "Chicken Supreme", R.drawable.burger3, 1400, "Un burger au poulet grillé accompagné d'avocat et de sauce épicée."),
                Menu(13, "Ramen", R.drawable.ramen, 1800, "Soupe de nouilles avec bouillon riche en saveurs."),
                Menu(14, "Tempura", R.drawable.tempura, 1500, "Friture légère de crevettes et légumes."),
                Menu(15, "Mochi", R.drawable.mochi, 800, "Dessert traditionnel japonais à base de riz gluant."),
                Menu(16, "Sushi Assorti", R.drawable.sushi_rolls, 2500, "Assortiment de sushis frais."),
                Menu(17, "Sashimi", R.drawable.sashimi, 2200, "Poisson frais tranché, servi sans riz."),
            )
        )
    )
    data.add(
        Restaurant(
            id = 4,
            name = "Taj Mahal",
            imgUrl = R.drawable.taj_mahal,
            description = "Cuisine indienne authentique avec un large choix de curry et naan.",
            typeCuisine = "Indienne",
            localisation = "Constantine, Algérie",
            noteMoy = 4.3,
            nombreComm = 150,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.taj_mahal_map,
            phone = "+213 555987654",
            openHour = "11:30",
            closeHour = "22:00",
            facebook = "tajmahal.constantine",
            instagram = "@tajmahal_dz",
            menus = listOf(
                Menu(18, "Spicy Burger", R.drawable.burger4, 1200, "Un burger épicé avec jalapeños et une sauce piquante."),
                Menu(19, "Biryani de Légumes", R.drawable.biryani_legumes, 1500, "Riz parfumé avec des légumes et épices."),
                Menu(20, "Lassi à la Mangue", R.drawable.lassi_mangue, 600, "Boisson à la mangue rafraîchissante."),
                Menu(21, "Poulet Tikka Masala", R.drawable.poulet_tikka_masala, 1800, "Poulet mariné dans une sauce épicée."),
                Menu(22, "Naan au Beurre", R.drawable.naan_beurre, 500, "Pain plat indien beurré."),
                Menu(23, "Curry d'Agneau", R.drawable.curry_agneau, 2200, "Curry d'agneau tendre avec épices indiennes."),
            )
        )
    )
    data.add(
        Restaurant(
            id = 5,
            name = "Machaoui El Boustane",
            imgUrl = R.drawable.beyrouth_lounge,
            description = "Une ambiance libanaise authentique avec des plats traditionnels.",
            typeCuisine = "Libanaise",
            localisation = "Annaba, Algérie",
            noteMoy = 4.8,
            nombreComm = 140,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.el_boustane_map,
            phone = "+213 555345678",
            openHour = "10:30",
            closeHour = "23:30",
            facebook = "elboustane.annaba",
            instagram = "@elboustane_machaoui",
            menus = listOf(
                Menu(24, "Double Beef Burger", R.drawable.burger5, 1600, "Un burger avec deux steaks juteux et du fromage fondu."),
                Menu(25, "Baklawa", R.drawable.baklawa, 500, "Dessert traditionnel libanais aux noix et miel."),
                Menu(26, "Mezzé Assorti", R.drawable.mezze_assorti, 1300, "Assortiment de mezzés libanais."),
                Menu(27, "Chawarma Poulet", R.drawable.shawarma, 1200, "Sandwich de poulet avec sauce tahini."),
                Menu(28, "Taboulé", R.drawable.taboule, 700, "Salade de persil, boulgour et légumes."),
                Menu(29, "Kebab", R.drawable.kebab, 1400, "Brochettes de viande grillées avec épices.")
            )
        )
    )
    data.add(
        Restaurant(
            id = 6,
            name = "El Dey",
            imgUrl = R.drawable.eldey,
            description = "Restaurant algérien authentique proposant des plats traditionnels.",
            typeCuisine = "Algérienne",
            localisation = "Alger, Algérie",
            noteMoy = 5.0,
            nombreComm = 120,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.el_dey_map,
            phone = "+213 555234567",
            openHour = "11:00",
            closeHour = "23:00",
            facebook = "eldey.alger",
            instagram = "@eldey_restaurant",
            menus = listOf(
                Menu(30, "Fish & Chips Burger", R.drawable.burger6, 1400, "Un burger avec du poisson frit et des frites croustillantes."),
                Menu(31, "Mhajeb", R.drawable.mhajeb, 500, "Crêpe feuilletée farcie aux légumes épicés."),
                Menu(32, "Mtewem", R.drawable.mtewem, 1400, "Boulettes de viande en sauce blanche à l'ail."),
                Menu(33, "Tamina", R.drawable.tamina, 400, "Dessert algérien à base de semoule grillée et miel."),
                Menu(34, "Kesra", R.drawable.kesra, 200, "Galette traditionnelle servie avec les plats."),
                Menu(35, "Rechta", R.drawable.rechta, 1100, "Pâtes algériennes avec sauce blanche et poulet.")
            )
        )
    )
    data.add(
        Restaurant(
            id = 7,
            name = "Sakura Sushi",
            imgUrl = R.drawable.sakura_sushi,
            description = "Un restaurant japonais spécialisé dans les sushis et ramen.",
            typeCuisine = "Japonaise",
            localisation = "Oran, Algérie",
            noteMoy = 4.4,
            nombreComm = 80,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.sakura_sushi,
            phone = "+213 555876543",
            openHour = "12:00",
            closeHour = "22:30",
            facebook = "sakurasushi.oran",
            instagram = "@sakura_sushi_dz",
            menus = listOf(
                Menu(36, "Sushi Rolls", R.drawable.sushi_rolls, 2200, "Roulés de sushi avec poisson frais et légumes."),
                Menu(37, "Ramen Tonkotsu", R.drawable.ramen_tonkotsu, 2000, "Nouilles ramen dans un bouillon riche et savoureux."),
                Menu(38, "Salade Wakame", R.drawable.salade_wakame, 1000, "Salade d'algues avec vinaigrette sésame."),
                Menu(39, "Tempura de Légumes", R.drawable.tempura_legumes, 1200, "Légumes frits légers et croustillants."),
                Menu(40, "Mochi Glacé", R.drawable.mochi_glace, 800, "Dessert traditionnel japonais à base de riz gluant avec une garniture glacée.")
            )
        )
    )
    data.add(
        Restaurant(
            id = 8,
            name = "AL Baik",
            imgUrl = R.drawable.damascus_delights,
            description = "Cuisine syrienne, offrant des plats typiques comme le shawarma.",
            typeCuisine = "Syrienne",
            localisation = "Constantine, Algérie",
            noteMoy = 5.0,
            nombreComm = 200,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.el_baik_map,
            phone = "+213 555432198",
            openHour = "10:00",
            closeHour = "23:00",
            facebook = "albaik.constantine",
            instagram = "@albaik_dz",
            menus = listOf(
                Menu(41, "Shawarma", R.drawable.shawarma, 1500, "Viande marinée grillée, servie dans un pain pita."),
                Menu(42, "Kibbeh", R.drawable.kibbeh, 1300, "Boulettes de viande hachée avec boulgour et épices."),
                Menu(43, "Tabbouleh", R.drawable.tabbouleh, 700, "Salade de persil, boulgour, tomates et citron."),
                Menu(44, "Fattoush", R.drawable.fattoush, 800, "Salade syrienne avec pain pita croustillant."),
                Menu(45, "Baklava", R.drawable.baklava, 600, "Pâtisserie feuilletée aux noix et sirop.")
            )
        )
    )
    data.add(
        Restaurant(
            id = 9,
            name = "La Casa del Food",
            imgUrl = R.drawable.la_casa_italienne,
            description = "Un restaurant italien réputé pour ses pizzas et pâtes fraîches.",
            typeCuisine = "Italienne",
            localisation = "Annaba, Algérie",
            noteMoy = 3.4,
            nombreComm = 60,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.la_casa_del_food_map,
            phone = "+213 555789123",
            openHour = "11:30",
            closeHour = "22:30",
            facebook = "lacasadelfood.annaba",
            instagram = "@lacasadelfood_dz",
            menus = listOf(
                Menu(46, "Pizza Margherita", R.drawable.pizza_margherita, 1600, "Pizza classique avec tomate, mozzarella et basilic."),
                Menu(47, "Pâtes Carbonara", R.drawable.pates_carbonara, 1800, "Pâtes avec une sauce crémeuse au bacon et parmesan."),
                Menu(48, "Lasagne", R.drawable.lasagne, 2000, "Lasagne avec viande, sauce béchamel et fromage."),
                Menu(49, "Tiramisu", R.drawable.tiramisu, 700, "Dessert italien à base de café et mascarpone."),
                Menu(50, "Bruschetta", R.drawable.bruschetta, 900, "Tranches de pain grillé avec tomates fraîches et basilic.")
            )
        )
    )

    data.add(
        Restaurant(
            id = 1,
            name = "Le Patio",
            imgUrl = R.drawable.lepatio,
            description = "Un restaurant chic au cœur d'Alger avec une cuisine méditerranéenne raffinée.",
            typeCuisine = "Méditerranéenne",
            localisation = "Alger, Algérie",
            noteMoy = 4.0,
            nombreComm = 250,
            deliverytime = "45 mns",
            deliveryprice = "230 DA",
            mapImg = R.drawable.le_patio_map,
            phone = "+213 555567890",
            openHour = "12:00",
            closeHour = "23:30",
            facebook = "lepatio.alger",
            instagram = "@lepatio_restaurant",
            menus =  listOf(
                Menu(51, "Salade Méditerranéenne", R.drawable.salade_mediterraneenne, 1200, "Salade fraîche avec des légumes méditerranéens."),
                Menu(52, "Poulet Grillé", R.drawable.poulet_grille, 1800, "Poulet grillé servi avec des légumes."),
                Menu(53, "Pâtes aux Fruits de Mer", R.drawable.pates_fruits_mer, 2000, "Pâtes fraîches avec une sauce aux fruits de mer."),
                Menu(54, "Ratatouille", R.drawable.ratatouille, 1500, "Ratatouille aux légumes du soleil, parfaite pour un déjeuner léger."),
                Menu(55, "Tarte aux Figues", R.drawable.tarte_figues, 800, "Délicieuse tarte sucrée aux figues de saison."),
                Menu(56, "Classic Burger", R.drawable.burger, 500, "Un burger classique avec un fromage fondant et une sauce savoureuse."),
                Menu(57, "Cheese Deluxe", R.drawable.burger2, 650, "Un burger garni de fromage cheddar fondant et de bacon croustillant."),
                Menu(58, "Chicken Supreme", R.drawable.burger3, 1400, "Un burger au poulet grillé accompagné d'avocat et de sauce épicée."),
                Menu(59, "Spicy Burger", R.drawable.burger4, 1200, "Un burger épicé avec jalapeños et une sauce piquante."),
                Menu(60, "Double Beef Burger", R.drawable.burger5, 1600, "Un burger avec deux steaks juteux et du fromage fondu."),
                Menu(61, "Fish & Chips Burger", R.drawable.burger6, 1400, "Un burger avec du poisson frit et des frites croustillantes."),
            )
        )
    )
    return data
}