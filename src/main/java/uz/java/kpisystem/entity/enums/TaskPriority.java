package uz.java.kpisystem.entity.enums;

public enum TaskPriority {
    URGENT("Shoshilinch", "Bistree", "Urgent"),
    HIGH("Yuqori", "", "High"),
    NORMAL("O`rtacha", "normal", "Normal"),
    LOW("Past", "", "Low");

    private String valueUz;
    private String valueRu;
    private String valueEn;

    TaskPriority(String valueUz, String valueRu, String valueEn) {
        this.valueUz = valueUz;
        this.valueRu = valueRu;
        this.valueEn = valueEn;
    }
    }
