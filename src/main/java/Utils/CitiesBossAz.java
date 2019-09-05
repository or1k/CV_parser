package Utils;

import java.util.HashMap;

public class CitiesBossAz {




    public static String getCitiesCode(String city){

        HashMap<String, String> cities = new HashMap<>();
        cities.put("Агдам", "12");
        cities.put("Агдаш", "83");
        cities.put("Агджабеди", "28");
        cities.put("Агстафа", "85");
        cities.put("Агсу", "34");
        cities.put("Баку", "1");
        cities.put("Барда", "31");
        cities.put("Бейлаган", "30");
        cities.put("Белоканы", "73");
        cities.put("Билясувар", "26");
        cities.put("Габала", "12");
        cities.put("Газах", "15");
        cities.put("Гедабек", "86");
        cities.put("Гейчай", "29");
        cities.put("Геранбой", "81");
        cities.put("Губа", "18");
        cities.put("Гусар", "19");
        cities.put("Гянджа", "4");
        cities.put("Дашкесан", "84");
        cities.put("Джалилабад", "72");
        cities.put("Евлах", "10");
        cities.put("Загатала", "2");
        cities.put("Имишли", "25");
        cities.put("Исмаиллы", "3");
        cities.put("Карадаг", "39");
        cities.put("Кюрдамир", "27");
        cities.put("Лерик", "82");
        cities.put("Лянкяран", "11");
        cities.put("Масаллы", "16");
        cities.put("Мингячевир", "6");
        cities.put("Нафталан", "14");
        cities.put("Нахчыван", "8");
        cities.put("Саатлы", "33");
        cities.put("Сабирабад", "24");
        cities.put("Сальян", "22");
        cities.put("Самух", "35");
        cities.put("Сиазань", "74");
        cities.put("Сумгайыт", "5");
        cities.put("Тертер", "32");
        cities.put("Товуз", "17");
        cities.put("Физули", "23");
        cities.put("Хачмаз", "20");
        cities.put("Хызы", "37");
        cities.put("Шамахы", "21");
        cities.put("Шамкир", "13");
        cities.put("Шарур", "79");
        cities.put("Шеки", "9");
        cities.put("Ширван", "78");
        cities.put("Ках ", "38");
        cities.put("Хырдалан ", "75");
        cities.put("Шабран ", "80");
        cities.put("Ярдымлы  ", "77");
        cities.put("Али-Байрамлы ", "7");
        cities.put("Все города", "Все города");


        return cities.get(city);

    }
}
