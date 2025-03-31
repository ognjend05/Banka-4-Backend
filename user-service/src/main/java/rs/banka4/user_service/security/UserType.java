package rs.banka4.user_service.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserType {
    EMPLOYEE,
    CLIENT;

    public GrantedAuthority asAuthority() {
        return this::name;
    }
}
