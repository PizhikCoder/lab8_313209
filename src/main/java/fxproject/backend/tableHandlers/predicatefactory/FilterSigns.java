package fxproject.backend.tableHandlers.predicatefactory;

public enum FilterSigns {
    MORE_THAN(">"),
    LESS_THAN("<"),
    EQUALITY("="),
    INEQUALITY("<>");
    String stringInterpretation;
    FilterSigns(String sign){
        this.stringInterpretation = sign;
    }

    public static FilterSigns findByTextInterpretation(String sign){
        for (FilterSigns filterSigns : values()){
            if (sign.equals(filterSigns.stringInterpretation)){
                return filterSigns;
            }
        }
        return null;
    }

    public String getStringInterpretation(){
        return stringInterpretation;
    }

}
