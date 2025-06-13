package com.example.zavrsnirad.repository

import com.example.zavrsnirad.model.ProductModel
import com.example.zavrsnirad.R

class ProductRepository : IProductRepository {
    // Funkcija za dohvacanje liste proizvoda
    override fun getProductList(): List<ProductModel> {
        return listOf(
            ProductModel(
                1,
                "Računalo LINKS Office O125IW",
                749,
                "VISOKE PERFORMANSE ZA UREDSKE ZADATKE I MULTIMEDIJSKE APLIKACIJE\n" +
                        "Računalo s Intel Core i5-13400 procesorom, 32 GB DDR4 RAM-a i 1 TB SSD-om NVMe pruža izvanredne performanse za zahtjevnije uredske zadatke, multitasking i multimedijske aplikacije. Uz integriranu Intel grafiku, korisnici mogu uživati u osnovnoj grafici za filmove, igre i druge multimedijske sadržaje.",
                R.drawable.img1
            ),
            ProductModel(
                2,
                "Laptop ACER Nitro V 15",
                1599,
                "IZVANREDNA GAMING I RADNA STANICA ZA NAJZAHTJEVNIJE KORISNIKE\n" +
                        "\n" +
                        "Acer Nitro V15 (model NH.QQEEX.00N) u ovoj konfiguraciji donosi vrhunsku snagu i performanse u elegantnom, robusnom kućištu, stvarajući savršen alat za ozbiljne gamere, kreatore sadržaja i sve korisnike koji traže moćan laptop za najzahtjevnije zadatke. S procesorom iz najvišeg razreda, naprednom RTX grafikom, zaslonom visoke frekvencije i velikim kapacitetom memorije i pohrane, ovaj model spreman je za sve izazove koje mu postavite – bilo da se radi o igrama najnovije generacije, renderiranju videa ili radu u profesionalnim softverima.\n",
                R.drawable.img2
            ),
            ProductModel(
                3,
                "Monitor 27\" DELL SE2725H",
                139,
                "PREDSTAVLJAMO DELL SE2725H: SAVRŠEN SPOJ UDOBNOSTI I PRODUKTIVNOSTI\n" +
                        "\n" +
                        "U današnjem digitalnom dobu, odabir pravog monitora ključan je za osiguravanje optimalne produktivnosti i udobnosti tijekom rada. Dell SE2725H 27-inčni monitor dizajniran je s ciljem pružanja izvanrednog vizualnog iskustva, istovremeno vodeći računa o zdravlju vaših očiju i estetskom izgledu vašeg radnog prostora.\n",
                R.drawable.img3
            ),
            ProductModel(
                4,
                "Tipkovnica LOGITECH Gaming G512 Carbon",
                99,
                "VRHUNSKA MEHANIČKA TIPKOVNICA ZA GAMING I PRODUKTIVNOST\n" +
                        "Logitech Gaming G512 Carbon RGB mehanička tipkovnica s GX Brown Tactile prekidačima donosi savršenu ravnotežu između brzine, preciznosti i udobnosti. S prilagodljivim RGB osvjetljenjem, robusnim aluminijskim okvirom i taktilnim povratnim osjećajem tipki, ova tipkovnica idealna je za kompetitivne gamere i profesionalce koji traže izdržljivost i estetiku u jednom uređaju.",
                R.drawable.img4
            ),
            ProductModel(
                5,
                "Miš LOGITECH M190",
                13,
                "LOGITECH M190 OPTIČKI BEŽIČNI MIŠ 1000 DPI\n" +
                        "Logitech M190 je bežični optički miš koji nudi pouzdanu preciznost i udobnost za svakodnevnu uporabu. Dostupan u crnoj, crno-sivoj, crno-plavoj i crno-crvenoj boji, ovaj miš pruža jednostavan dizajn uz vrhunske performanse, idealan za korisnike koji traže praktično rješenje za rad na računaru.",
                R.drawable.img5
            ),
            ProductModel(
                6,
                "Slušalice CORSAIR HS55 Wireless",
                89,
                "Corsair HS55 Wireless slušalice predstavljaju vrhunski izbor za gamere i ljubitelje glazbe koji traže bežičnu slobodu, izvanrednu kvalitetu zvuka i dugotrajnu udobnost. Ove slušalice kombiniraju napredne audio tehnologije s ergonomskim dizajnom, pružajući korisnicima sveobuhvatno i impresivno iskustvo.\n" +
                        "\n" +
                        "IZVANREDNA KVALITETA ZVUKA\n" +
                        "\n" +
                        "Opremljene prilagođenim 50 mm zvučnicima, HS55 Wireless slušalice pružaju bogat i detaljan zvuk unutar frekvencijskog raspona od 20 Hz do 20 kHz. Ova široka frekvencijska pokrivenost omogućuje preciznu reprodukciju svih zvučnih detalja, od dubokih basova do visokih tonova, osiguravajući potpuno uživljavanje u igru ili glazbu.",
                R.drawable.img6
            )
        )
    }
}