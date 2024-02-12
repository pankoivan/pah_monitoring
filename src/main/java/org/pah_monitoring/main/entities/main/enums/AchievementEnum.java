package org.pah_monitoring.main.entities.main.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AchievementEnum {

    HISTORIAN("Историк"),

    INHALE_EXHALE("Вдох-выдох"),

    WALK("Прогулка"),

    UNCONSCIOUS("Без сознания"),

    TRANSFORMATION("Трансформация"),

    ATHLETE("Спортсмен"),

    WATER_BALANCE("Водный баланс"),

    DIET("Вес"),

    DESPERATE("Отчаянный"),

    BEGINNER("Начинающий"),

    VERIFIED("Проверенный"),

    DILIGENT("Старательный"),

    PERSISTENT("Упорный"),

    PURPOSEFUL("Целеустремлённый"),

    UNSHAKABLE("Непоколебимый"),

    CHAMPION("Чемпион");

    private final String alias;

}
