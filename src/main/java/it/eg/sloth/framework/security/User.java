package it.eg.sloth.framework.security;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Enrico Grillini
 */
@Getter
@Setter
public class User extends FrameComponent {

    BigDecimal id;
    String userid;
    String surname;
    String name;
    String email;
    String emailPassword;
    Locale locale;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    boolean avatar;

    String addInfo;

    private Set<String> enabledFunction;
    private Menu menu;
    private Menu userMenu;

    public User() {
        this(null, null, null, null, null, null, Locale.getDefault(), false, null);

        this.enabledFunction = new HashSet<>();
        this.menu = new Menu();
    }

    public User(BigDecimal id, String userid, String surname, String name, String email, String emailPassword, Locale locale, boolean avatar, String addInfo) {
        this.id = id;
        this.userid = userid;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.emailPassword = emailPassword;
        this.locale = locale;
        this.avatar = avatar;
        this.addInfo = addInfo;

        this.enabledFunction = new HashSet<>();
        this.menu = new Menu();
        this.userMenu = new Menu();
    }

    public boolean isLogged() {
        return getId() != null;
    }

    public boolean accessAllowed(String functionName) {
        return enabledFunction.contains(functionName);
    }

    public void addFunction(String functionName) {
        enabledFunction.add(functionName);
    }

    public boolean hasAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public String getAvatarLetter() {
        return StringUtil.substr(name, 0, 1) + StringUtil.substr(surname, 0, 1);
    }

}
