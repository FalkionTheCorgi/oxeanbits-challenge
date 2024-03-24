package com.example.oxeanbits_challenge.bottom_sheet.model

val capitalsData = arrayOf(
    CapitalData(0.00, 0.00, "Selecione"),
    CapitalData(-10.9472, -37.0731, "Aracaju-SE"),
    CapitalData(-8.7608, -63.8696, "Porto Velho-RO"),
    CapitalData(-22.9068, -43.1729, "Rio de Janeiro-RJ"),
    CapitalData(-25.4284, -49.2733, "Curitiba-PR"),
    CapitalData(-5.0919, -42.8034, "Teresina-PI"),
    CapitalData(-30.0346, -51.2177, "Porto Alegre-RS"),
    CapitalData(-23.5505, -46.6333, "São Paulo-SP"),
    CapitalData(-19.9167, -43.9345, "Belo Horizonte-MG"),
    CapitalData(-3.7172, -38.5436, "Fortaleza-CE"),
    CapitalData(-5.7945, -35.211, "Natal-RN"),
    CapitalData(-7.115, -34.863, "João Pessoa-PB"),
    CapitalData(-15.601, -56.0974, "Cuiabá-MT"),
    CapitalData(-9.6658, -35.7353, "Maceió-AL"),
    CapitalData(-0.0356, -51.0703, "Macapá-AP"),
    CapitalData(-10.2128, -48.3605, "Palmas-TO"),
    CapitalData(-20.4697, -54.6201, "Campo Grande-MS"),
    CapitalData(2.8235, -60.6758, "Boa Vista-RR"),
    CapitalData(-8.0476, -34.877, "Recife-PE"),
    CapitalData(-15.794, -47.8825, "Brasília-DF"),
    CapitalData(-3.119, -60.0219, "Manaus-AM"),
    CapitalData(-1.4558, -48.5044, "Belém-PA"),
    CapitalData(-16.6864, -49.2643, "Goiânia-GO"),
    CapitalData(-2.5387, -44.2826, "São Luís-MA"),
    CapitalData(-9.9747, -67.8249, "Rio Branco-AC"),
    CapitalData(-20.3155, -40.3128, "Vitória-ES"),
    CapitalData(-27.5954, -48.548, "Florianópolis-SC"),
    CapitalData(-12.9792, -38.5108, "Salvador-BA")
)


data class CapitalData(
    var latitude: Double,
    var longitude: Double,
    var capital: String
)
