package com.mendao.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 鏈夊叧鏁扮粍澶勭悊鐨勫伐鍏风被銆�
 *
 * <p>
 * 杩欎釜绫讳腑鐨勬瘡涓柟娉曢兘鍙互鈥滃畨鍏ㄢ�濆湴澶勭悊<code>null</code>锛岃�屼笉浼氭姏鍑�<code>NullPointerException</code>銆�
 * </p>
 *
 * @author Harrison han
 * @version 1.0
 */
public class ArrayUtil {
    /* ============================================================================ */
    /*  甯搁噺鍜宻ingleton銆�                                                           */
    /* ============================================================================ */

    /** 绌虹殑<code>Object</code>鏁扮粍銆� */
    public static final Object[]    EMPTY_OBJECT_ARRAY             = new Object[0];

    /** 绌虹殑<code>Class</code>鏁扮粍銆� */
    public static final Class[]     EMPTY_CLASS_ARRAY              = new Class[0];

    /** 绌虹殑<code>String</code>鏁扮粍銆� */
    public static final String[]    EMPTY_STRING_ARRAY             = new String[0];

    /** 绌虹殑<code>long</code>鏁扮粍銆� */
    public static final long[]      EMPTY_LONG_ARRAY               = new long[0];

    /** 绌虹殑<code>Long</code>鏁扮粍銆� */
    public static final Long[]      EMPTY_LONG_OBJECT_ARRAY        = new Long[0];

    /** 绌虹殑<code>int</code>鏁扮粍銆� */
    public static final int[]       EMPTY_INT_ARRAY                = new int[0];

    /** 绌虹殑<code>Integer</code>鏁扮粍銆� */
    public static final Integer[]   EMPTY_INTEGER_OBJECT_ARRAY     = new Integer[0];

    /** 绌虹殑<code>short</code>鏁扮粍銆� */
    public static final short[]     EMPTY_SHORT_ARRAY              = new short[0];

    /** 绌虹殑<code>Short</code>鏁扮粍銆� */
    public static final Short[]     EMPTY_SHORT_OBJECT_ARRAY       = new Short[0];

    /** 绌虹殑<code>byte</code>鏁扮粍銆� */
    public static final byte[]      EMPTY_BYTE_ARRAY               = new byte[0];

    /** 绌虹殑<code>Byte</code>鏁扮粍銆� */
    public static final Byte[]      EMPTY_BYTE_OBJECT_ARRAY        = new Byte[0];

    /** 绌虹殑<code>double</code>鏁扮粍銆� */
    public static final double[]    EMPTY_DOUBLE_ARRAY             = new double[0];

    /** 绌虹殑<code>Double</code>鏁扮粍銆� */
    public static final Double[]    EMPTY_DOUBLE_OBJECT_ARRAY      = new Double[0];

    /** 绌虹殑<code>float</code>鏁扮粍銆� */
    public static final float[]     EMPTY_FLOAT_ARRAY              = new float[0];

    /** 绌虹殑<code>Float</code>鏁扮粍銆� */
    public static final Float[]     EMPTY_FLOAT_OBJECT_ARRAY       = new Float[0];

    /** 绌虹殑<code>boolean</code>鏁扮粍銆� */
    public static final boolean[]   EMPTY_BOOLEAN_ARRAY            = new boolean[0];

    /** 绌虹殑<code>Boolean</code>鏁扮粍銆� */
    public static final Boolean[]   EMPTY_BOOLEAN_OBJECT_ARRAY     = new Boolean[0];

    /** 绌虹殑<code>char</code>鏁扮粍銆� */
    public static final char[]      EMPTY_CHAR_ARRAY               = new char[0];

    /** 绌虹殑<code>Character</code>鏁扮粍銆� */
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY   = new Character[0];

    /** 璁＄畻hashcode鎵�鐢ㄧ殑甯搁噺銆� */
    private static final int        INITIAL_NON_ZERO_ODD_NUMBER    = 17;

    /** 璁＄畻hashcode鎵�鐢ㄧ殑甯搁噺銆� */
    private static final int        MULTIPLIER_NON_ZERO_ODD_NUMBER = 37;

    /* ============================================================================ */
    /*  鍒ょ┖鍑芥暟銆�                                                                  */
    /*                                                                              */
    /*  鍒ゆ柇涓�涓暟缁勬槸鍚︿负null鎴栧寘鍚�0涓厓绱犮��                                       */
    /* ============================================================================ */

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new String[0])     = true
     * ArrayUtil.isEmpty(new String[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(Object[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new long[0])     = true
     * ArrayUtil.isEmpty(new long[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(long[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new int[0])     = true
     * ArrayUtil.isEmpty(new int[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(int[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new short[0])     = true
     * ArrayUtil.isEmpty(new short[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(short[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new byte[0])     = true
     * ArrayUtil.isEmpty(new byte[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(byte[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new double[0])     = true
     * ArrayUtil.isEmpty(new double[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(double[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new float[0])     = true
     * ArrayUtil.isEmpty(new float[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(float[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new boolean[0])     = true
     * ArrayUtil.isEmpty(new boolean[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(boolean[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿负<code>null</code>鎴栫┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = true
     * ArrayUtil.isEmpty(new char[0])     = true
     * ArrayUtil.isEmpty(new char[10])    = false
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓虹┖, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isEmpty(char[] array) {
        return ((array == null) || (array.length == 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new String[0])     = false
     * ArrayUtil.isEmpty(new String[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(Object[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new long[0])     = false
     * ArrayUtil.isEmpty(new long[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(long[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new int[0])     = false
     * ArrayUtil.isEmpty(new int[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(int[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new short[0])     = false
     * ArrayUtil.isEmpty(new short[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(short[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new byte[0])     = false
     * ArrayUtil.isEmpty(new byte[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(byte[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new double[0])     = false
     * ArrayUtil.isEmpty(new double[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(double[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new float[0])     = false
     * ArrayUtil.isEmpty(new float[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(float[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new boolean[0])     = false
     * ArrayUtil.isEmpty(new boolean[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(boolean[] array) {
        return ((array != null) && (array.length > 0));
    }

    /**
     * 妫�鏌ユ暟缁勬槸鍚︿笉鏄�<code>null</code>鍜岀┖鏁扮粍<code>[]</code>銆�
     * <pre>
     * ArrayUtil.isEmpty(null)              = false
     * ArrayUtil.isEmpty(new char[0])     = false
     * ArrayUtil.isEmpty(new char[10])    = true
     * </pre>
     *
     * @param array 瑕佹鏌ョ殑鏁扮粍
     *
     * @return 濡傛灉涓嶄负绌�, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean isNotEmpty(char[] array) {
        return ((array != null) && (array.length > 0));
    }

    /* ============================================================================ */
    /*  榛樿鍊煎嚱鏁般��                                                                */
    /*                                                                              */
    /*  褰撴暟缁勪负null鎴杄mpty鏃讹紝灏嗘暟缁勮浆鎹㈡垚鎸囧畾鐨勯粯璁ゆ暟缁勩��                         */
    /* ============================================================================ */

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new String[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new String[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static Object[] defaultIfNull(Object[] array) {
        return (array == null) ? EMPTY_OBJECT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new long[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new long[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static long[] defaultIfNull(long[] array) {
        return (array == null) ? EMPTY_LONG_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new int[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new int[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static int[] defaultIfNull(int[] array) {
        return (array == null) ? EMPTY_INT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new short[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new short[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static short[] defaultIfNull(short[] array) {
        return (array == null) ? EMPTY_SHORT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new byte[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new byte[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static byte[] defaultIfNull(byte[] array) {
        return (array == null) ? EMPTY_BYTE_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new double[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new double[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static double[] defaultIfNull(double[] array) {
        return (array == null) ? EMPTY_DOUBLE_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new float[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new float[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static float[] defaultIfNull(float[] array) {
        return (array == null) ? EMPTY_FLOAT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new boolean[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new boolean[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static boolean[] defaultIfNull(boolean[] array) {
        return (array == null) ? EMPTY_BOOLEAN_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null)           = []
     * ArrayUtil.defaultIfNull(new char[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new char[10]) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static char[] defaultIfNull(char[] array) {
        return (array == null) ? EMPTY_CHAR_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfNull(new String[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new String[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static Object[] defaultIfNull(Object[] array, Object[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)         = defaultArray
     * ArrayUtil.defaultIfNull(new long[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new long[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static long[] defaultIfNull(long[] array, long[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)        = defaultArray
     * ArrayUtil.defaultIfNull(new int[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new int[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static int[] defaultIfNull(int[] array, int[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)          = defaultArray
     * ArrayUtil.defaultIfNull(new short[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new short[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static short[] defaultIfNull(short[] array, short[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)         = defaultArray
     * ArrayUtil.defaultIfNull(new byte[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new byte[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static byte[] defaultIfNull(byte[] array, byte[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)         = defaultArray
     * ArrayUtil.defaultIfNull(new double[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new double[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static double[] defaultIfNull(double[] array, double[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)          = defaultArray
     * ArrayUtil.defaultIfNull(new float[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new float[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static float[] defaultIfNull(float[] array, float[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)            = defaultArray
     * ArrayUtil.defaultIfNull(new boolean[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new boolean[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static boolean[] defaultIfNull(boolean[] array, boolean[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, defaultArray)         = defaultArray
     * ArrayUtil.defaultIfNull(new char[0], defaultArray)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new char[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static char[] defaultIfNull(char[] array, char[] defaultArray) {
        return (array == null) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖鎸囧畾鍏冪礌绫诲瀷鐨勭┖鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, String.class)           = new String[0]
     * ArrayUtil.defaultIfNull(new String[0], String.class)  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfNull(new String[10], String.class) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultComponentType 榛樿鏁扮粍鐨勫厓绱犵被鍨�
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱被鍨嬬殑绌烘暟缁�
     */
    public static Object[] defaultIfNull(Object[] array, Class defaultComponentType) {
        return (array == null) ? (Object[]) Array.newInstance(
            ClassUtil.getNonPrimitiveType(defaultComponentType), 0) : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)           = []
     * ArrayUtil.defaultIfEmpty(new String[0])  = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new String[10]) = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static Object[] defaultIfEmpty(Object[] array) {
        return (array == null) ? EMPTY_OBJECT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)           = []
     * ArrayUtil.defaultIfEmpty(new long[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new long[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static long[] defaultIfEmpty(long[] array) {
        return (array == null) ? EMPTY_LONG_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)          = []
     * ArrayUtil.defaultIfEmpty(new int[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new int[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static int[] defaultIfEmpty(int[] array) {
        return (array == null) ? EMPTY_INT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)               = []
     * ArrayUtil.defaultIfEmpty(new short[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new short[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static short[] defaultIfEmpty(short[] array) {
        return (array == null) ? EMPTY_SHORT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)           = []
     * ArrayUtil.defaultIfEmpty(new byte[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new byte[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static byte[] defaultIfEmpty(byte[] array) {
        return (array == null) ? EMPTY_BYTE_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)               = []
     * ArrayUtil.defaultIfEmpty(new double[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new double[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static double[] defaultIfEmpty(double[] array) {
        return (array == null) ? EMPTY_DOUBLE_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)               = []
     * ArrayUtil.defaultIfEmpty(new float[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new float[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static float[] defaultIfEmpty(float[] array) {
        return (array == null) ? EMPTY_FLOAT_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)               = []
     * ArrayUtil.defaultIfEmpty(new boolean[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new boolean[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static boolean[] defaultIfEmpty(boolean[] array) {
        return (array == null) ? EMPTY_BOOLEAN_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖绌烘暟缁�<code>[]</code>锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     *
     * <p>
     * 姝ゆ柟娉曞疄闄呬笂鍜�<code>defaultIfNull(Object[])</code>绛夋晥銆�
     * <pre>
     * ArrayUtil.defaultIfEmpty(null)           = []
     * ArrayUtil.defaultIfEmpty(new char[0])    = 鏁扮粍鏈韩
     * ArrayUtil.defaultIfEmpty(new char[10])   = 鏁扮粍鏈韩
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栫┖鏁扮粍<code>[]</code>
     */
    public static char[] defaultIfEmpty(char[] array) {
        return (array == null) ? EMPTY_CHAR_ARRAY : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new String[0], defaultArray)  = defaultArray
     * ArrayUtil.defaultIfEmpty(new String[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static Object[] defaultIfEmpty(Object[] array, Object[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new long[0], defaultArray)    = defaultArray
     * ArrayUtil.defaultIfEmpty(new long[10], defaultArray)   = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static long[] defaultIfEmpty(long[] array, long[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new int[0], defaultArray)     = defaultArray
     * ArrayUtil.defaultIfEmpty(new int[10], defaultArray)    = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static int[] defaultIfEmpty(int[] array, int[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new short[0], defaultArray)   = defaultArray
     * ArrayUtil.defaultIfEmpty(new short[10], defaultArray)  = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static short[] defaultIfEmpty(short[] array, short[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new byte[0], defaultArray)    = defaultArray
     * ArrayUtil.defaultIfEmpty(new byte[10], defaultArray)   = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static byte[] defaultIfEmpty(byte[] array, byte[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new double[0], defaultArray)  = defaultArray
     * ArrayUtil.defaultIfEmpty(new double[10], defaultArray) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static double[] defaultIfEmpty(double[] array, double[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new float[0], defaultArray)   = defaultArray
     * ArrayUtil.defaultIfEmpty(new float[10], defaultArray)  = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static float[] defaultIfEmpty(float[] array, float[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)              = defaultArray
     * ArrayUtil.defaultIfEmpty(new boolean[0], defaultArray)    = defaultArray
     * ArrayUtil.defaultIfEmpty(new boolean[10], defaultArray)   = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static boolean[] defaultIfEmpty(boolean[] array, boolean[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾榛樿鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfEmpty(null, defaultArray)           = defaultArray
     * ArrayUtil.defaultIfEmpty(new char[0], defaultArray)    = defaultArray
     * ArrayUtil.defaultIfEmpty(new char[10], defaultArray)   = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultArray 榛樿鏁扮粍
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱殑榛樿鏁扮粍
     */
    public static char[] defaultIfEmpty(char[] array, char[] defaultArray) {
        return ((array == null) || (array.length == 0)) ? defaultArray : array;
    }

    /**
     * 濡傛灉鏁扮粍鏄�<code>null</code>鎴栫┖鏁扮粍<code>[]</code>锛屽垯杩斿洖鎸囧畾鍏冪礌绫诲瀷鐨勭┖鏁扮粍锛屽惁鍒欒繑鍥炴暟缁勬湰韬��
     * <pre>
     * ArrayUtil.defaultIfNull(null, String.class)           = new String[0]
     * ArrayUtil.defaultIfNull(new String[0], String.class)  = new String[0]
     * ArrayUtil.defaultIfNull(new String[10], String.class) = 鏁扮粍鏈韩
     * </pre>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param defaultComponentType 榛樿鏁扮粍鐨勫厓绱犵被鍨�
     *
     * @return 鏁扮粍鏈韩鎴栨寚瀹氱被鍨嬬殑绌烘暟缁�
     */
    public static Object[] defaultIfEmpty(Object[] array, Class defaultComponentType) {
        return ((array == null) || (array.length == 0)) ? (Object[]) Array.newInstance(
            ClassUtil.getNonPrimitiveType(defaultComponentType), 0) : array;
    }

    /* ============================================================================ */
    /*  姣旇緝鍑芥暟銆�                                                                  */
    /*                                                                              */
    /*  浠ヤ笅鏂规硶鐢ㄦ潵姣旇緝涓や釜鏁扮粍鏄惁瀹屽叏鐩稿悓锛屾敮鎸佸缁存暟缁勩��                        */
    /* ============================================================================ */

    /**
     * 閫掑綊鍦版瘮杈冧袱涓暟缁勬槸鍚︾浉鍚岋紝鏀寔澶氱淮鏁扮粍銆�
     *
     * <p>
     * 濡傛灉姣旇緝鐨勫璞′笉鏄暟缁勶紝鍒欐鏂规硶鐨勭粨鏋滃悓<code>ObjectUtil.equals</code>銆�
     * </p>
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉鐩哥瓑, 鍒欒繑鍥�<code>true</code>
     */
    public static boolean equals(Object array1, Object array2) {
        if (array1 == array2) {
            return true;
        }

        if ((array1 == null) || (array2 == null)) {
            return false;
        }

        Class clazz = array1.getClass();

        if (!clazz.equals(array2.getClass())) {
            return false;
        }

        if (!clazz.isArray()) {
            return array1.equals(array2);
        }

        // array1鍜宎rray2涓哄悓绫诲瀷鐨勬暟缁�
        if (array1 instanceof long[]) {
            long[] longArray1 = (long[]) array1;
            long[] longArray2 = (long[]) array2;

            if (longArray1.length != longArray2.length) {
                return false;
            }

            for (int i = 0; i < longArray1.length; i++) {
                if (longArray1[i] != longArray2[i]) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof int[]) {
            int[] intArray1 = (int[]) array1;
            int[] intArray2 = (int[]) array2;

            if (intArray1.length != intArray2.length) {
                return false;
            }

            for (int i = 0; i < intArray1.length; i++) {
                if (intArray1[i] != intArray2[i]) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof short[]) {
            short[] shortArray1 = (short[]) array1;
            short[] shortArray2 = (short[]) array2;

            if (shortArray1.length != shortArray2.length) {
                return false;
            }

            for (int i = 0; i < shortArray1.length; i++) {
                if (shortArray1[i] != shortArray2[i]) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof byte[]) {
            byte[] byteArray1 = (byte[]) array1;
            byte[] byteArray2 = (byte[]) array2;

            if (byteArray1.length != byteArray2.length) {
                return false;
            }

            for (int i = 0; i < byteArray1.length; i++) {
                if (byteArray1[i] != byteArray2[i]) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof double[]) {
            double[] doubleArray1 = (double[]) array1;
            double[] doubleArray2 = (double[]) array2;

            if (doubleArray1.length != doubleArray2.length) {
                return false;
            }

            for (int i = 0; i < doubleArray1.length; i++) {
                if (Double.doubleToLongBits(doubleArray1[i]) != Double
                    .doubleToLongBits(doubleArray2[i])) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof float[]) {
            float[] floatArray1 = (float[]) array1;
            float[] floatArray2 = (float[]) array2;

            if (floatArray1.length != floatArray2.length) {
                return false;
            }

            for (int i = 0; i < floatArray1.length; i++) {
                if (Float.floatToIntBits(floatArray1[i]) != Float.floatToIntBits(floatArray2[i])) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof boolean[]) {
            boolean[] booleanArray1 = (boolean[]) array1;
            boolean[] booleanArray2 = (boolean[]) array2;

            if (booleanArray1.length != booleanArray2.length) {
                return false;
            }

            for (int i = 0; i < booleanArray1.length; i++) {
                if (booleanArray1[i] != booleanArray2[i]) {
                    return false;
                }
            }

            return true;
        } else if (array1 instanceof char[]) {
            char[] charArray1 = (char[]) array1;
            char[] charArray2 = (char[]) array2;

            if (charArray1.length != charArray2.length) {
                return false;
            }

            for (int i = 0; i < charArray1.length; i++) {
                if (charArray1[i] != charArray2[i]) {
                    return false;
                }
            }

            return true;
        } else {
            Object[] objectArray1 = (Object[]) array1;
            Object[] objectArray2 = (Object[]) array2;

            if (objectArray1.length != objectArray2.length) {
                return false;
            }

            for (int i = 0; i < objectArray1.length; i++) {
                if (!equals(objectArray1[i], objectArray2[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    /* ============================================================================ */
    /*  Hashcode鍑芥暟銆�                                                              */
    /*                                                                              */
    /*  浠ヤ笅鏂规硶鐢ㄦ潵鍙栧緱鏁扮粍鐨刪ash code銆�                                           */
    /* ============================================================================ */

    /**
     * 鍙栧緱鏁扮粍鐨刪ash鍊�, 濡傛灉鏁扮粍涓�<code>null</code>, 鍒欒繑鍥�<code>0</code>銆�
     *
     * <p>
     * 濡傛灉瀵硅薄涓嶆槸鏁扮粍锛屽垯姝ゆ柟娉曠殑缁撴灉鍚�<code>ObjectUtil.hashCode</code>銆�
     * </p>
     *
     * @param array 鏁扮粍
     *
     * @return hash鍊�
     */
    public static int hashCode(Object array) {
        if (array == null) {
            return 0;
        }

        if (!array.getClass().isArray()) {
            return array.hashCode();
        }

        int hashCode = INITIAL_NON_ZERO_ODD_NUMBER;

        // array鏄暟缁�
        if (array instanceof long[]) {
            long[] longArray = (long[]) array;

            for (int i = 0; i < longArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER)
                           + ((int) (longArray[i] ^ (longArray[i] >> 32)));
            }
        } else if (array instanceof int[]) {
            int[] intArray = (int[]) array;

            for (int i = 0; i < intArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + intArray[i];
            }
        } else if (array instanceof short[]) {
            short[] shortArray = (short[]) array;

            for (int i = 0; i < shortArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + shortArray[i];
            }
        } else if (array instanceof byte[]) {
            byte[] byteArray = (byte[]) array;

            for (int i = 0; i < byteArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + byteArray[i];
            }
        } else if (array instanceof double[]) {
            double[] doubleArray = (double[]) array;

            for (int i = 0; i < doubleArray.length; i++) {
                long longBits = Double.doubleToLongBits(doubleArray[i]);

                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER)
                           + ((int) (longBits ^ (longBits >> 32)));
            }
        } else if (array instanceof float[]) {
            float[] floatArray = (float[]) array;

            for (int i = 0; i < floatArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER)
                           + Float.floatToIntBits(floatArray[i]);
            }
        } else if (array instanceof boolean[]) {
            boolean[] booleanArray = (boolean[]) array;

            for (int i = 0; i < booleanArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + (booleanArray[i] ? 1 : 0);
            }
        } else if (array instanceof char[]) {
            char[] charArray = (char[]) array;

            for (int i = 0; i < charArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + charArray[i];
            }
        } else {
            Object[] objectArray = (Object[]) array;

            for (int i = 0; i < objectArray.length; i++) {
                hashCode = (hashCode * MULTIPLIER_NON_ZERO_ODD_NUMBER) + hashCode(objectArray[i]);
            }
        }

        return hashCode;
    }

    /* ============================================================================ */
    /*  灏嗘暟缁勮浆鎹㈡垚闆嗗悎绫汇��                                                        */
    /* ============================================================================ */

    /**
     * 灏嗘暟缁勬槧灏勬垚鍥哄畾闀垮害鐨�<code>List</code>锛屽綋鏀瑰彉杩欎釜<code>List</code>涓殑鍊兼椂銆傛暟缁勪腑鐨勭浉搴斿�间篃琚敼鍙樸��
     *
     * <p>
     * 濡傛灉杈撳叆鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * </p>
     *
     * <p>
     * 璇ユ柟娉曞唴閮ㄨ皟鐢�<code>java.util.Arrays.asList</code>鏂规硶鎵�杩斿洖鐨勫垪琛ㄤ负鎸囧畾鏁扮粍鐨勬槧鍍忥紙鍥哄畾闀垮害锛夛紝鍥犳鎬ц兘鍜屽唴瀛樺崰鐢ㄤ笂姣�<code>toList</code>鏂规硶鏇翠紭銆�
     * </p>
     *
     * <p>
     * 杩欎釜鏂规硶甯歌鐢ㄤ簬鍒濆鍖栵紝渚嬪锛�
     * <pre>
     * List myList = ArrayUtil.toFixedList(new String[] { "aaa", "bbb", "ccc" });
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 浠ユ暟缁勬湰韬负鏄犲皠鐨刲ist
     */
    public static List toFixedList(Object[] array) {
        if (array == null) {
            return null;
        }

        return Arrays.asList(array);
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚<code>List</code>銆�
     *
     * <p>
     * 濡傛灉杈撳叆鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * </p>
     *
     * <p>
     * 璇ユ柟娉曡繑鍥炵殑鍒楄〃涓烘寚瀹氭暟缁勭殑澶嶆湰锛岃��<code>java.util.Arrays.asList</code>鏂规硶鎵�杩斿洖鐨勫垪琛ㄤ负鎸囧畾鏁扮粍鐨勬槧鍍忥紙鍥哄畾闀垮害锛夈��
     * </p>
     *
     * <p>
     * 杩欎釜鏂规硶甯歌鐢ㄤ簬鍒濆鍖栵紝渚嬪锛�
     * <pre>
     * List myList      = ArrayUtil.toList(new String[] { "aaa", "bbb", "ccc" });
     * List singleList  = ArrayUtil.toList("hello");     // 杩斿洖鍗曚釜鍏冪礌鐨勫垪琛╗"hello"]
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 琚垱寤虹殑list
     */
    public static List toList(Object array) {
        return toList(array, null);
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚<code>List</code>銆�
     *
     * <p>
     * 濡傛灉杈撳叆鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * </p>
     *
     * <p>
     * 璇ユ柟娉曡繑鍥炵殑鍒楄〃涓烘寚瀹氭暟缁勭殑澶嶆湰锛岃��<code>java.util.Arrays.asList</code>鏂规硶鎵�杩斿洖鐨勫垪琛ㄤ负鎸囧畾鏁扮粍鐨勬槧鍍忥紙鍥哄畾闀垮害锛夈��
     * </p>
     *
     * <p>
     * 杩欎釜鏂规硶甯歌鐢ㄤ簬鍒濆鍖栵紝渚嬪锛�
     * <pre>
     * List myList      = ArrayUtil.toList(new String[] { "aaa", "bbb", "ccc" }, new ArrayList());
     * List singleList  = ArrayUtil.toList("hello", new ArrayList());     // 杩斿洖鍗曚釜鍏冪礌鐨勫垪琛╗"hello"]
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param list 瑕佸～鍏呯殑鍒楄〃锛屽鏋滄槸<code>null</code>锛屽垯鍒涘缓涔�
     *
     * @return 琚垱寤烘垨濉厖鐨刲ist
     */
    public static List toList(Object array, List list) {
        if (array == null) {
            return list;
        }

        // 闈瀉rray锛屽垱寤轰竴涓彧鏈変竴涓厓绱犵殑鍒楄〃
        if (!array.getClass().isArray()) {
            if (list == null) {
                list = new ArrayList(1);
            }

            list.add(array);
        } else if (array instanceof long[]) {
            long[] longArray = (long[]) array;

            if (list == null) {
                list = new ArrayList(longArray.length);
            }

            for (int i = 0; i < longArray.length; i++) {
                list.add(new Long(longArray[i]));
            }
        } else if (array instanceof int[]) {
            int[] intArray = (int[]) array;

            if (list == null) {
                list = new ArrayList(intArray.length);
            }

            for (int i = 0; i < intArray.length; i++) {
                list.add(new Integer(intArray[i]));
            }
        } else if (array instanceof short[]) {
            short[] shortArray = (short[]) array;

            if (list == null) {
                list = new ArrayList(shortArray.length);
            }

            for (int i = 0; i < shortArray.length; i++) {
                list.add(new Short(shortArray[i]));
            }
        } else if (array instanceof byte[]) {
            byte[] byteArray = (byte[]) array;

            if (list == null) {
                list = new ArrayList(byteArray.length);
            }

            for (int i = 0; i < byteArray.length; i++) {
                list.add(new Byte(byteArray[i]));
            }
        } else if (array instanceof double[]) {
            double[] doubleArray = (double[]) array;

            if (list == null) {
                list = new ArrayList(doubleArray.length);
            }

            for (int i = 0; i < doubleArray.length; i++) {
                list.add(new Double(doubleArray[i]));
            }
        } else if (array instanceof float[]) {
            float[] floatArray = (float[]) array;

            if (list == null) {
                list = new ArrayList(floatArray.length);
            }

            for (int i = 0; i < floatArray.length; i++) {
                list.add(new Float(floatArray[i]));
            }
        } else if (array instanceof boolean[]) {
            boolean[] booleanArray = (boolean[]) array;

            if (list == null) {
                list = new ArrayList(booleanArray.length);
            }

            for (int i = 0; i < booleanArray.length; i++) {
                list.add(booleanArray[i] ? Boolean.TRUE : Boolean.FALSE);
            }
        } else if (array instanceof char[]) {
            char[] charArray = (char[]) array;

            if (list == null) {
                list = new ArrayList(charArray.length);
            }

            for (int i = 0; i < charArray.length; i++) {
                list.add(new Character(charArray[i]));
            }
        } else {
            Object[] objectArray = (Object[]) array;

            if (list == null) {
                list = new ArrayList(objectArray.length);
            }

            for (int i = 0; i < objectArray.length; i++) {
                list.add(objectArray[i]);
            }
        }

        return list;
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚<code>Map</code>銆傛暟缁勭殑鍏冪礌蹇呴』鏄�<code>Map.Entry</code>鎴栧厓绱犱釜鏁板浜�2鐨勫瓙鏁扮粍銆�
     *
     * <p>
     * 濡傛灉杈撳叆鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * </p>
     *
     * <p>
     * 杩欎釜鏂规硶甯歌鐢ㄤ簬鍒濆鍖栵紝渚嬪锛�
     * <pre>
     * Map colorMap = ArrayUtil.toMap(new String[][] {
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}});
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 琚垱寤虹殑map
     *
     * @throws IllegalArgumentException 濡傛灉鏈変竴涓瓙鏁扮粍鍏冪礌涓暟灏忎簬2鎴栦笉鏄�<code>Map.Entry</code>瀹炰緥
     */
    public static Map toMap(Object[] array) {
        return toMap(array, null);
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚<code>Map</code>銆傛暟缁勭殑鍏冪礌蹇呴』鏄�<code>Map.Entry</code>鎴栧厓绱犱釜鏁板浜�2鐨勫瓙鏁扮粍銆�
     *
     * <p>
     * 濡傛灉杈撳叆鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     * </p>
     *
     * <p>
     * 杩欎釜鏂规硶甯歌鐢ㄤ簬鍒濆鍖栵紝渚嬪锛�
     * <pre>
     * Map colorMap = ArrayUtil.toMap(new String[][] {{
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}}, new HashMap());
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param map 瑕佸～鍏呯殑map锛屽鏋滀负<code>null</code>鍒欒嚜鍔ㄥ垱寤轰箣
     *
     * @return 琚垱寤烘垨濉厖鐨刴ap
     *
     * @throws IllegalArgumentException 濡傛灉鏈変竴涓瓙鏁扮粍鍏冪礌涓暟灏忎簬2鎴栦笉鏄�<code>Map.Entry</code>瀹炰緥
     */
    public static Map toMap(Object[] array, Map map) {
        if (array == null) {
            return map;
        }

        if (map == null) {
            map = new HashMap((int) (array.length * 1.5));
        }

        for (int i = 0; i < array.length; i++) {
            Object object = array[i];

            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) object;

                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                Object[] entry = (Object[]) object;

                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + object
                                                       + "', has a length less than 2");
                }

                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '" + object
                                                   + "', is neither of type Map.Entry nor an Array");
            }
        }

        return map;
    }

    /* ============================================================================ */
    /*  Clone鍑芥暟銆�                                                                 */
    /*                                                                              */
    /*  浠ヤ笅鏂规硶璋冪敤Object.clone鏂规硶锛岃繘琛屸�滄祬澶嶅埗鈥濓紙shallow copy锛夈��              */
    /* ============================================================================ */

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曞彧杩涜鈥滄祬澶嶅埗鈥濓紝涔熷氨鏄锛屾暟缁勪腑鐨勫璞℃湰韬笉浼氳澶嶅埗銆� 鍙﹀锛屾鏂规硶涔熶笉澶勭悊澶氱淮鏁扮粍銆�
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static Object[] clone(Object[] array) {
        if (array == null) {
            return null;
        }

        return (Object[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }

        return (long[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }

        return (int[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }

        return (short[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }

        return (byte[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }

        return (double[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }

        return (float[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }

        return (boolean[]) array.clone();
    }

    /**
     * 澶嶅埗涓�涓暟缁勩�傚鏋滄暟缁勪负<code>null</code>锛屽垯杩斿洖<code>null</code>銆�
     *
     * <p>
     * 姝ゆ柟娉曚篃涓嶅鐞嗗缁存暟缁勩��
     * </p>
     *
     * @param array 瑕佸鍒剁殑鏁扮粍
     *
     * @return 鏁扮粍鐨勫鏈紝濡傛灉鍘熷鏁扮粍涓�<code>null</code>锛屽垯杩斿洖<code>null</code>
     */
    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }

        return (char[]) array.clone();
    }

    /* ============================================================================ */
    /*  姣旇緝鏁扮粍鐨勯暱搴︺��                                                            */
    /* ============================================================================ */

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(Object[] array1, Object[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(long[] array1, long[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(int[] array1, int[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(short[] array1, short[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(byte[] array1, byte[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(double[] array1, double[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(float[] array1, float[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /**
     * 鍒ゆ柇涓や釜鏁扮粍鏄惁鍏锋湁鐩稿悓鐨勯暱搴︺�傚鏋滄暟缁勪负<code>null</code>鍒欒鐪嬩綔闀垮害涓�<code>0</code>銆�
     *
     * @param array1 鏁扮粍1
     * @param array2 鏁扮粍2
     *
     * @return 濡傛灉涓や釜鏁扮粍闀垮害鐩稿悓锛屽垯杩斿洖<code>true</code>
     */
    public static boolean isSameLength(char[] array1, char[] array2) {
        int length1 = (array1 == null) ? 0 : array1.length;
        int length2 = (array2 == null) ? 0 : array2.length;

        return length1 == length2;
    }

    /* ============================================================================ */
    /*  鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋��                                                        */
    /* ============================================================================ */

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(Object[] array) {
        if (array == null) {
            return;
        }

        Object tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }

        long tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }

        int tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }

        short tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }

        byte tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }

        double tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }

        float tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }

        boolean tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /**
     * 鍙嶈浆鏁扮粍鐨勫厓绱犻『搴忋�傚鏋滄暟缁勪负<code>null</code>锛屽垯浠�涔堜篃涓嶅仛銆�
     *
     * @param array 瑕佸弽杞殑鏁扮粍
     */
    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }

        char tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歄bject[]                                                              */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param objectToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(Object[] array, Object[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param objectToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(Object[] array, Object[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        Object first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && !ObjectUtil.equals(array[i], first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (!ObjectUtil.equals(array[j++], arrayToFind[k++])) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param objectToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(Object[] array, Object[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param objectToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(Object[] array, Object[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        Object last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && !ObjectUtil.equals(array[i], last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (!ObjectUtil.equals(array[j--], arrayToFind[k--])) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param objectToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(Object[] array, Object[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歭ong[]                                                                */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param longToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(long[] array, long longToFind) {
        return indexOf(array, longToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param longToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(long[] array, long[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        long first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param longToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(long[] array, long longToFind) {
        return lastIndexOf(array, longToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(long[] array, long[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param longToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(long[] array, long[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        long last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param longToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(long[] array, long longToFind) {
        return indexOf(array, longToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歩nt[]                                                                 */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param intToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(int[] array, int intToFind) {
        return indexOf(array, intToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param intToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(int[] array, int[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param intToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(int[] array, int intToFind) {
        return lastIndexOf(array, intToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(int[] array, int[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param intToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(int[] array, int[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        int last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param intToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(int[] array, int intToFind) {
        return indexOf(array, intToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歴hort[]                                                               */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param shortToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(short[] array, short shortToFind) {
        return indexOf(array, shortToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param shortToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(short[] array, short[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        short first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param shortToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(short[] array, short shortToFind) {
        return lastIndexOf(array, shortToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(short[] array, short[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param shortToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(short[] array, short[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        short last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param shortToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(short[] array, short shortToFind) {
        return indexOf(array, shortToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歜yte[]                                                                */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param byteToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param byteToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        byte first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param byteToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(byte[] array, byte byteToFind) {
        return lastIndexOf(array, byteToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(byte[] array, byte[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param byteToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        byte last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param byteToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歞ouble[]                                                              */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind, 0, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, 0, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double doubleToFind, int startIndex) {
        return indexOf(array, doubleToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double doubleToFind, int startIndex, double tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(double[] array, double[] arrayToFind, int startIndex, double tolerance) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        double firstMin = arrayToFind[0] - tolerance;
        double firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && ((array[i] < firstMin) || (array[i] > firstMax))) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double doubleToFind) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double doubleToFind, double tolerance) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, double tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex) {
        return lastIndexOf(array, doubleToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex,
                                  double tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex,
                                  double tolerance) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        double lastMin = arrayToFind[lastIndex] - tolerance;
        double lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && ((array[i] < lastMin) || (array[i] > lastMax))) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param doubleToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, tolerance) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, tolerance) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歠loat[]                                                               */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float floatToFind) {
        return indexOf(array, floatToFind, 0, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, 0, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float floatToFind, int startIndex) {
        return indexOf(array, floatToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(float[] array, float[] arrayToFind, int startIndex, float tolerance) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        float firstMin = arrayToFind[0] - tolerance;
        float firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && ((array[i] < firstMin) || (array[i] > firstMax))) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float floatToFind) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float floatToFind, float tolerance) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, float tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float floatToFind, int startIndex) {
        return lastIndexOf(array, floatToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     * @param tolerance 璇樊
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex,
                                  float tolerance) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        float lastMin = arrayToFind[lastIndex] - tolerance;
        float lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && ((array[i] < lastMin) || (array[i] > lastMax))) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(float[] array, float floatToFind) {
        return indexOf(array, floatToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param floatToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param tolerance 璇樊
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, tolerance) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param tolerance 璇樊
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, tolerance) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歜oolean[]                                                             */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param booleanToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param booleanToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        boolean first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param booleanToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(boolean[] array, boolean booleanToFind) {
        return lastIndexOf(array, booleanToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param booleanToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        boolean last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param booleanToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犳垨涓�涓厓绱犲簭鍒椼��                                        */
    /*                                                                              */
    /*  绫诲瀷锛歝har[]                                                                */
    /* ============================================================================ */

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param charToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(char[] array, char charToFind) {
        return indexOf(array, charToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param charToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑鏌ユ壘涓�涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欑湅浣�<code>0</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int indexOf(char[] array, char[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return (targetLength == 0) ? sourceLength : (-1);
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        char first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            // 鏌ユ壘绗竴涓厓绱�
            while ((i <= max) && (array[i] != first)) {
                i++;
            }

            if (i > max) {
                return -1;
            }

            // 宸茬粡鎵惧埌绗竴涓厓绱狅紝鎺ョ潃鎵�
            int j = i + 1;
            int end = (j + targetLength) - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    // 閲嶆柊鏌ユ壘绗竴涓厓绱�
                    continue startSearchForFirst;
                }
            }

            // 鎵惧埌浜�
            return i;
        }
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param charToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(char[] array, char charToFind) {
        return lastIndexOf(array, charToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(char[] array, char[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犮��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param charToFind 瑕佹煡鎵剧殑鍏冪礌
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            return -1;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 鍦ㄦ暟缁勪腑浠庢湯灏惧紑濮嬫煡鎵句竴涓厓绱犲簭鍒椼��
     *
     * <p>
     * 濡傛灉鏈壘鍒版垨鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>-1</code>銆�
     * </p>
     *
     * <p>
     * 璧峰绱㈠紩灏忎簬<code>0</code>鍒欒繑鍥�<code>-1</code>锛岃秴鍑烘暟缁勯暱搴︾殑璧峰绱㈠紩鍒欎粠鏁扮粍鏈熬寮�濮嬫壘銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     * @param startIndex 璧峰绱㈠紩
     *
     * @return 璇ュ厓绱犲簭鍒楀湪鏁扮粍涓殑搴忓彿锛屽鏋滄暟缁勪负<code>null</code>鎴栨湭鎵惧埌锛屽垯杩斿洖<code>-1</code>銆�
     */
    public static int lastIndexOf(char[] array, char[] arrayToFind, int startIndex) {
        if ((array == null) || (arrayToFind == null)) {
            return -1;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return -1;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        char last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while ((i >= min) && (array[i] != last)) {
                i--;
            }

            if (i < min) {
                return -1;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }

    /**
     * 鍒ゆ柇鎸囧畾瀵硅薄鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param charToFind 瑕佹煡鎵剧殑鍏冪礌
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(char[] array, char charToFind) {
        return indexOf(array, charToFind) != -1;
    }

    /**
     * 鍒ゆ柇鎸囧畾鍏冪礌搴忓垪鏄惁瀛樺湪浜庢寚瀹氭暟缁勪腑銆�
     *
     * <p>
     * 濡傛灉鏁扮粍涓�<code>null</code>鍒欒繑鍥�<code>false</code>銆�
     * </p>
     *
     * @param array 瑕佹壂鎻忕殑鏁扮粍
     * @param arrayToFind 瑕佹煡鎵剧殑鍏冪礌搴忓垪
     *
     * @return 濡傛灉鎵惧埌鍒欒繑鍥�<code>true</code>
     */
    public static boolean contains(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind) != -1;
    }

    /* ============================================================================ */
    /*  灏嗘暟缁勮浆鎹㈡垚鏄撲簬闃呰鐨勫瓧绗︿覆琛ㄧず銆�                                          */
    /*                                                                              */
    /*  鏀寔澶氱淮鏁扮粍銆�                                                              */
    /* ============================================================================ */

    /**
     * 灏嗘暟缁勮浆鎹㈡垚鏄撲簬闃呰鐨勫瓧绗︿覆琛ㄧず銆�
     *
     * <p>
     * 濡傛灉鏁扮粍鏄�<code>null</code>鍒欒繑鍥�<code>[]</code>锛屾敮鎸佸缁存暟缁勩��
     * 濡傛灉鏁扮粍鍏冪礌涓�<code>null</code>锛屽垯鏄剧ず<code>&lt;null&gt;</code>銆�
     * <pre>
     * ArrayUtil.toString(null)                              = "[]"
     * ArrayUtil.toString(new int[] {1, 2, 3})               = "[1, 2, 3]"
     * ArrayUtil.toString(new boolean[] {true, false, true}) = "[true, false, true]"
     * ArrayUtil.toString(new Object[] {
     *                       {1, 2, 3},  // 宓屽鏁扮粍
     *                       hello,      // 宓屽闈炴暟缁�
     *                       null,       // 宓屽null
     *                       {},         // 宓屽绌烘暟缁�
     *                       {2, 3, 4}   // 宓屽鏁扮粍
     *                    })                                 = "[[1, 2, 3], hello, <null>, [], [2, 3, 4]]"
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     *
     * @return 瀛楃涓茶〃绀猴紝<code>"[]"</code>琛ㄧず绌烘暟缁勬垨<code>null</code>
     */
    public static String toString(Object array) {
        return toString(array, "[]", "<null>");
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚鏄撲簬闃呰鐨勫瓧绗︿覆琛ㄧず銆�
     *
     * <p>
     * 濡傛灉鏁扮粍鏄�<code>null</code>鍒欒繑鍥炴寚瀹氬瓧绗︿覆锛屾敮鎸佸缁存暟缁勩��
     * 濡傛灉鏁扮粍鍏冪礌涓�<code>null</code>锛屽垯鏄剧ず<code>&lt;null&gt;</code>銆�
     * <pre>
     * ArrayUtil.toString(null, "null")                              = "null"
     * ArrayUtil.toString(new int[] {1, 2, 3}, "null")               = "[1, 2, 3]"
     * ArrayUtil.toString(new boolean[] {true, false, true}, "null") = "[true, false, true]"
     * ArrayUtil.toString(new Object[] {
     *                       {1, 2, 3},  // 宓屽鏁扮粍
     *                       hello,      // 宓屽闈炴暟缁�
     *                       null,       // 宓屽null
     *                       {},         // 宓屽绌烘暟缁�
     *                       {2, 3, 4}   // 宓屽鏁扮粍
     *                    }, "null")                                 = "[[1, 2, 3], hello, <null>, [], [2, 3, 4]]"
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param nullArrayStr 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖姝ゅ瓧绗︿覆
     *
     * @return 瀛楃涓茶〃绀猴紝鎴栬繑鍥炴寚瀹氬瓧绗︿覆琛ㄧず<code>null</code>
     */
    public static String toString(Object array, String nullArrayStr) {
        return toString(array, nullArrayStr, "<null>");
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚鏄撲簬闃呰鐨勫瓧绗︿覆琛ㄧず銆�
     *
     * <p>
     * 濡傛灉鏁扮粍鏄�<code>null</code>鍒欒繑鍥炴寚瀹氬瓧绗︿覆锛屾敮鎸佸缁存暟缁勩�� 濡傛灉鏁扮粍鍏冪礌涓�<code>null</code>锛屽垯鏄剧ず鎸囧畾瀛楃涓层��
     * <pre>
     * ArrayUtil.toString(null, "null", "NULL")                              = "null"
     * ArrayUtil.toString(new int[] {1, 2, 3}, "null", "NULL")               = "[1, 2, 3]"
     * ArrayUtil.toString(new boolean[] {true, false, true}, "null", "NULL") = "[true, false, true]"
     * ArrayUtil.toString(new Object[] {
     *                       {1, 2, 3},  // 宓屽鏁扮粍
     *                       hello,      // 宓屽闈炴暟缁�
     *                       null,       // 宓屽null
     *                       {},         // 宓屽绌烘暟缁�
     *                       {2, 3, 4}   // 宓屽鏁扮粍
     *                    }, "null", "NULL")                                 = "[[1, 2, 3], hello, NULL, [], [2, 3, 4]]"
     * </pre>
     * </p>
     *
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param nullArrayStr 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖姝ゅ瓧绗︿覆
     * @param nullElementStr 濡傛灉鏁扮粍涓殑鍏冪礌涓�<code>null</code>锛屽垯杩斿洖姝ゅ瓧绗︿覆
     *
     * @return 瀛楃涓茶〃绀猴紝鎴栬繑鍥炴寚瀹氬瓧绗︿覆琛ㄧず<code>null</code>
     */
    public static String toString(Object array, String nullArrayStr, String nullElementStr) {
        if (array == null) {
            return nullArrayStr;
        }

        StringBuffer buffer = new StringBuffer();

        toString(buffer, array, nullArrayStr, nullElementStr);

        return buffer.toString();
    }

    /**
     * 灏嗘暟缁勮浆鎹㈡垚鏄撲簬闃呰鐨勫瓧绗︿覆琛ㄧず銆�<code>null</code>灏嗚鐪嬩綔绌烘暟缁勩�� 鏀寔澶氱淮鏁扮粍銆�
     *
     * @param buffer 灏嗚浆鎹㈠悗鐨勫瓧绗︿覆鍔犲叆鍒拌繖涓�<code>StringBuffer</code>涓�
     * @param array 瑕佽浆鎹㈢殑鏁扮粍
     * @param nullArrayStr 濡傛灉鏁扮粍鏄�<code>null</code>锛屽垯杩斿洖姝ゅ瓧绗︿覆
     * @param nullElementStr 濡傛灉鏁扮粍涓殑鍏冪礌涓�<code>null</code>锛屽垯杩斿洖姝ゅ瓧绗︿覆
     */
    private static void toString(StringBuffer buffer, Object array, String nullArrayStr,
                                 String nullElementStr) {
        if (array == null) {
            buffer.append(nullElementStr);
            return;
        }

        if (!array.getClass().isArray()) {
            buffer.append(ObjectUtil.toString(array, nullElementStr));
            return;
        }

        buffer.append('[');

        // array涓烘暟缁�
        if (array instanceof long[]) {
            long[] longArray = (long[]) array;
            int length = longArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(longArray[i]);
            }
        } else if (array instanceof int[]) {
            int[] intArray = (int[]) array;
            int length = intArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(intArray[i]);
            }
        } else if (array instanceof short[]) {
            short[] shortArray = (short[]) array;
            int length = shortArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(shortArray[i]);
            }
        } else if (array instanceof byte[]) {
            byte[] byteArray = (byte[]) array;
            int length = byteArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                } else {
                    buffer.append("0x");
                }

                String hexStr = Integer.toHexString(0xFF & byteArray[i]).toUpperCase();

                if (hexStr.length() == 0) {
                    buffer.append("00");
                } else if (hexStr.length() == 1) {
                    buffer.append("0");
                }

                buffer.append(hexStr);
            }
        } else if (array instanceof double[]) {
            double[] doubleArray = (double[]) array;
            int length = doubleArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(doubleArray[i]);
            }
        } else if (array instanceof float[]) {
            float[] floatArray = (float[]) array;
            int length = floatArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(floatArray[i]);
            }
        } else if (array instanceof boolean[]) {
            boolean[] booleanArray = (boolean[]) array;
            int length = booleanArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(booleanArray[i]);
            }
        } else if (array instanceof char[]) {
            char[] charArray = (char[]) array;
            int length = charArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                buffer.append(charArray[i]);
            }
        } else {
            Object[] objectArray = (Object[]) array;
            int length = objectArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    buffer.append(", ");
                }

                toString(buffer, objectArray[i], nullArrayStr, nullElementStr);
            }
        }

        buffer.append(']');
    }
}
