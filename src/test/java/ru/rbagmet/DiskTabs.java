package ru.rbagmet;

public enum DiskTabs {
	RECENT("Последние"),
	FILES("Файлы"),
	PHOTO("Фото"),
	ALBUMS("Альбомы"),
	PUBLIC("Общий доступ"),
	JOURNAL("История"),
	ATTACH("Архив"),
	TRASH("Корзина");

	private String text;

    DiskTabs(String text) {
        this.text = text;
    }

    public String getTabText() {
        return text;
    }

}