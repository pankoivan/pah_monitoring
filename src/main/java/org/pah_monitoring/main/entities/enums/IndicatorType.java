package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum IndicatorType {

    SPIROMETRY("Спирометрия"),

    WALK_TEST("Т6МХ"),

    PULSE_OXIMETRY("Пульсоксиметрия"),

    COUGH("Кашель"),

    CHEST_PAIN("Боль в груди"),

    FAINTING("Обморок"),

    PHYSICAL_CHANGES("Физические изменения"),

    ASCITES("Асцит"),

    OVERALL_HEALTH("Общее самочувствие"),

    VERTIGO("Головокружение"),

    PRESSURE("Давление"),

    LIQUID_AND_WEIGHT("Жидкость и вес"),

    FUNCTIONAL_CLASS("Функциональный класс"),

    BLOOD_TEST("Развёрнутый анализ крови"),

    ELECTROCARDIOGRAPHY("Электрокардиография"),

    RADIOGRAPHY("Рентгенография органов грудной клетки"),

    ECHOCARDIOGRAPHY("Эхокардиография"),

    COMPUTED_TOMOGRAPHY("Компьютерная томография органов грудной клетки"),

    CATHETERIZATION("Катетеризация правых отделов сердца");

    private final String alias;

}
