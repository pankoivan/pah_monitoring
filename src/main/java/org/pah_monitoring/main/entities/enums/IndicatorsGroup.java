package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum IndicatorsGroup {

    SPIROMETRY_AND_DLCO("Спирометрия + DLCO", "spirometry_and_DLCO"),

    WALK_TEST("Т6МХ", "walk_test"),

    PULSE_OXIMETRY("Пульсоксиметрия", "pulse_oximetry"),

    COUGH("Кашель", "cough"),

    CHEST_PAIN("Боль в груди", "chest_pain"),

    FAINTING("Обморок", "fainting"),

    PHYSICAL_CHANGES("Физические изменения", "physical_changes"),

    ASCITES("Асцит", "ascites"),

    OVERALL_HEALTH("Общее самочувствие", "overall_health"),

    VERTIGO("Головокружение", "vertigo"),

    PRESSURE("Давление", "pressure"),

    LIQUID_AND_WEIGHT("Жидкость и вес", "liquid_and_weight"),

    FUNCTIONAL_CLASS("Функциональный класс", "functional_class"),

    ANALYSIS_BLOOD_TEST("Развёрнутый анализ крови", "analysis_file"),

    ANALYSIS_ELECTROCARDIOGRAPHY("Электрокардиография", "analysis_file"),

    ANALYSIS_RADIOGRAPHY("Рентгенография органов грудной клетки", "analysis_file"),

    ANALYSIS_ECHOCARDIOGRAPHY("Эхокардиография", "analysis_file"),

    ANALYSIS_COMPUTED_TOMOGRAPHY("Компьютерная томография органов грудной клетки", "analysis_file"),

    ANALYSIS_CATHETERIZATION("Катетеризация правых отделов сердца", "analysis_file");

    private final String alias;

    private final String tableName;

}
