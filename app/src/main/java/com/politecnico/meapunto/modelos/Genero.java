package com.politecnico.meapunto.modelos;

public enum Genero
{
    HOMBRE,MUJER;

    public static Genero ToGenero (String myEnumString) {
        try {
            return valueOf(myEnumString);
        } catch (Exception ex) {
            // For error cases
            return HOMBRE;
        }
    }
}
