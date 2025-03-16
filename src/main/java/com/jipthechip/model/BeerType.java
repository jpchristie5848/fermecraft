package com.jipthechip.model;

public enum BeerType {

    // ALES

    // no roasted malt
    BLONDE_ALE, // low hops 15-30 IBU, 4-8 SRM
    PALE_ALE, // moderate hops 30-45 IBU, 8-10 SRM
    INDIAN_PALE_ALE, // high hops 45+ IBU, 8-10 SRM

    // toasted malt
    AMBER_ALE, // moderate hops 18-30 IBU 14-22 SRM
    IRISH_RED_ALE, // low hops 18-30 IBU 10-14 SRM
    IMPERIAL_RED_ALE, // high hops 30-60 IBU 14-22 SRM

    // roasted malt
    BLACK_IPA, // high hops 50+ IBU 35-40 SRM
    PORTER, // moderate hops 20-35 IBU 23+ SRM
    STOUT, // moderate/high hops 35-50 IBU 30+ SRM

    // LAGERS

    // no roasted malt
    PILSNER, // 30-45 IBU 3-6 SRM
    KOLSCH, // 18-30 IBU 4-7 SRM
    PALE_LAGER, // 8-18 IBU 3-6 SRM

    // toasted malt
    MUNICH_HELLES, // low/moderate hops 15-30 IBU 7-12 SRM
    MARZEN, // low hops 18-28 IBU 12-18 SRM

    // roasted malt
    DUNKEL, // 15-25 IBU 18-24 SRM
    SCHWARZBIER, // 20-30 IBU 25-40 SRM
    BALTIC_PORTER // 30+ IBU 30-40 SRM
}
