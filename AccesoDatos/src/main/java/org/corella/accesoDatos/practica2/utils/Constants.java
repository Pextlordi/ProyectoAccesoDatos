package org.corella.accesoDatos.practica2.utils;

public class Constants {
    public static final String rutaDataPersonal = "src/main/java/org/corella/accesoDatos/practica2/out/PersonalData.dat";
    public static final String rutaDataFinanciero = "src/main/java/org/corella/accesoDatos/practica2/out/FinancieroData.dat";
    public static final String rutaCSVPersonal = "src/main/java/org/corella/accesoDatos/practica2/resources/Personal.csv";
    public static final String rutaCSVFinanciero = "src/main/java/org/corella/accesoDatos/practica2/resources/Financiero.csv";
    public static final String rutaRangosIrpf = "src/main/java/org/corella/accesoDatos/practica2/resources/Irpf.txt";
    public static final String rutaRangosProvincias = "src/main/java/org/corella/accesoDatos/practica2/resources/Provincias.txt";
    public static final String rutaInformeMorosos = "src/main/java/org/corella/accesoDatos/practica2/out/informes/InformeMorosos.txt";
    public static final String rutaInformeProvincia = "src/main/java/org/corella/accesoDatos/practica2/out/informes/InformeProvincia.txt";
    public static final String rutaInformeSaldo = "src/main/java/org/corella/accesoDatos/practica2/out/informes/InformeSaldo.txt";

    public enum Irpf {
        IRPF_5, IRPF_10, IRPF_15
    }

    public enum Provincias {
        ALAVA, ALBACETE, ALICANTE, ALMERIA, ASTURIAS, AVILA,
        BADAJOZ, BALEARES, BARCELONA, BURGOS, CACERES, CADIZ,
        CANTABRIA, CASTELLON, CEUTA, CIUDAD_REAL, CORDOBA,
        CUENCA, GERONA, GRANADA, GUADALAJARA, GUIPUZCOA,
        HUELVA, HUESCA, JAEN, LA_CORUNA, LA_RIOJA, LAS_PALMAS,
        LEON, LERIDA, LUGO, MADRID, MALAGA, MELILLA, MURCIA,
        NAVARRA, ORENSE, PALENCIA, PONTEVEDRA, SALAMANCA,
        SEGOVIA, SEVILLA, SORIA, TARRAGONA, SANTA_CRUZ_DE_TENERIFE,
        TERUEL, TOLEDO, VALENCIA, VALLADOLID, VIZCAYA, ZAMORA, ZARAGOZA
    }

    public enum Fondo {
        PENSION, INVERSION
    }
}
