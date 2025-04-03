package com.echevarne.sap.cloud.facturacion.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

/**
 * Class for the Entity {@link StringUtils}.
 *
 * <p>
 * . . .
 * </p>
 * <p>
 * This is a simple description of the class. . .
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 *
 */
public class StringUtils {

	public final static String ANY = "*";
	public final static String NONE = "#";
	public final static String QUOTE = "'";
	public final static String EMPTY = "";
	public final static String SPACE = " ";

	private StringUtils() {
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		return value == null ? true : value.equals("");
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(@Nullable String value) {

		return value == null || value.isEmpty();

	}

	/**
	 *
	 * @param str
	 * @param value
	 * @return
	 */
	public static boolean equalsAnyOrNull(String str, String value) {
		return str == null || (value != null && value.equals(str));
	}

	/**
	 *
	 * @param str
	 * @param value
	 * @return
	 */
	public static boolean equalsAnyOrNull(int str, int value) {
		return str == 0 || (value != 0 && value == str);
	}

	/**
	 *
	 * @param set
	 * @param value
	 * @return
	 */
	public static boolean equalsAnyOrValue(Set<? extends BasicEntity> set, Object value) {

		return set.stream().anyMatch(item -> {
			return Arrays.stream(item.getClass().getDeclaredFields())
					.filter(field -> field.getAnnotation(Matcheable.class) != null).anyMatch(field -> {
						try {
							final Object fieldValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(item, field.getName());
							return equalsAnyOrValue(fieldValue, value);
						} catch (NoSuchFieldException | InvocationTargetException| IllegalArgumentException | IllegalAccessException e) {
							return false;
						}
					});
		});
	}

	public static boolean notEqualsAnyOrValue(Set<? extends BasicEntity> set, Object value) {

		return set.stream().noneMatch(item -> {
			return Arrays.stream(item.getClass().getDeclaredFields())
					.filter(field -> field.getAnnotation(Matcheable.class) != null).anyMatch(field -> {
						try {
							final Object fieldValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(item, field.getName());
							return equalsAnyOrValue(fieldValue, value);
						} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
							return false;
						}
					});
		});
	}

	/**
	 *
	 * @param str
	 * @param value
	 * @return
	 */
	public static boolean equalsAnyOrValue(Object str, Object value) {
		if (str.getClass() == String.class && value.getClass() == String.class)
			return equalsAnyOrValue((String) str, (String) value);
		else if (str.getClass() == String.class && value.getClass() == Integer.class)
			return equalsAnyOrValue(Integer.parseInt((String) str), (int) value);
		else if (str.getClass() == int.class)
			return equalsAnyOrValue((int) str, Integer.parseInt((String) value));
		else if (str.getClass() == Integer.class)
			return equalsAnyOrValue((Integer) str, (Integer) value);
		else if (str.getClass() == Boolean.class && value.getClass() == Boolean.class)
			return equalsAnyOrValue((Boolean) str, (Boolean) value);
		return false;
	}

	public static boolean equalsAnyOrValue(String str, String value) {
        return ANY.equals(str) || ((value == null || value.equals(EMPTY)) && NONE.equals(str)) ||
				(value != null && (equalsOrContain(str, value)));
	}

	public static boolean equalsAnyOrValue(boolean incluye, String str, String value) {
		boolean result = ANY.equals(str) || ((value == null || value.equals(EMPTY)) && NONE.equals(str)) ||
				(value != null && (equalsOrContain(str, value)));
		return incluye? result: !result;
	}

	public static boolean equalsOrContain(String str, String value) {
		if (str.contains(ANY)) {
			if (str.startsWith(ANY) && str.endsWith(ANY)){
				return value.contains(str.replace(ANY, EMPTY));
			} else if (str.startsWith(ANY)){
				return value.endsWith(str.replace(ANY, EMPTY));
			} else {
				return value.startsWith(str.replace(ANY, EMPTY));
			}
		} else {
			return value.equals(str);
		}
	}

	public static boolean equalsAnyOrValue(int str, int value) {
		return str == 0 || (value != 0 && value == str);
	}

	public static boolean equalsAnyOrValue(Integer str, Integer value) {
		return str == 0 || (value != 0 && value.equals(str));
	}

	public static boolean equalsAnyOrValue(Boolean value1, Boolean value2) {
		return Objects.equals(value1, value2);
	}

	/**
	 *
	 * @param s
	 * @param radix
	 * @return
	 */
	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static int toInteger(String value) {
		if (isInteger(value))
			return Integer.parseInt(value);
		else
			return 0;
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static final boolean isEmptyOrWhitespaceOnly(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String capitalize(String name) {
		if (name == null)
			return null;
		if (name.isEmpty())
			return name;
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static String formatStringValueWithLeadingZeros(String value, int numDigits) {
		if (value.isEmpty()) {
			return null;
		}
		if (isInteger(value)) {
			return String.format("%0" + numDigits + "d", Long.valueOf(value));
		} else {
			return value;
		}
	}
	
	

}
