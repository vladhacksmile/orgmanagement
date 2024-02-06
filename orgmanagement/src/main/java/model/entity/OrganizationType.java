package model.entity;

public enum OrganizationType {
    PUBLIC(1),
    GOVERNMENT(2),
    TRUST(3),
    PRIVATE_LIMITED_COMPANY(4),
    OPEN_JOINT_STOCK_COMPANY(5);

    private int id;

    OrganizationType(int id) {
        this.id = id;
    }
}
