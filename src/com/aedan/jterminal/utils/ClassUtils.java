package com.aedan.jterminal.utils;

import com.aedan.jterminal.environment.Environment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aedan Smith.
 */

public final class ClassUtils {
    /**
     * @param clazz The class to test.
     * @return True if the class is a number.
     */
    public static boolean isNumber(Class<?> clazz) {
        if (clazz.equals(Number.class))
            return true;
        if (Number.class.isAssignableFrom(clazz))
            return true;
        return clazz.isPrimitive() && !clazz.equals(char.class) && !clazz.equals(boolean.class);
    }

    public static Class<?> fromName(String name, Environment environment) throws ClassNotFoundException {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            for (String s : environment.getEnvironmentVariables().get("CP").toString().split(";")) {
                try {
                    return Class.forName(s + "." + name);
                } catch (ClassNotFoundException ignored) {
                }
            }
            throw new ClassNotFoundException();
        }
    }

    public static Object[] convertToObjectArray(Object array) {
        Class ofArray = array.getClass().getComponentType();
        if (ofArray.isPrimitive()) {
            List ar = new ArrayList();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                ar.add(Array.get(array, i));
            }
            return ar.toArray();
        } else {
            return (Object[]) array;
        }
    }

    /**
     * @param param The Parameter to test.
     * @param clazz The Class to test.
     * @return If the Class is a valid parameter.
     */
    public static boolean isValidParam(Class<?> param, Class<?> clazz) {
        return !param.isAssignableFrom(clazz)
                && !param.equals(clazz)
                && !isBoxable(param, clazz);
    }

    /**
     * @param primitive The primitive to test.
     * @param clazz     The class to attempt to box.
     * @return If it is possible to box the primitive.
     */
    public static boolean isBoxable(Class<?> primitive, Class<?> clazz) {
        if (!primitive.isPrimitive())
            return false;

        if (primitive.equals(boolean.class)) {
            return clazz.equals(Boolean.class);
        } else if (primitive.equals(char.class)) {
            return clazz.equals(Character.class);
        } else {
            return Number.class.isAssignableFrom(clazz);
        }
    }
}