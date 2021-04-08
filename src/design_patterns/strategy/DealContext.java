package design_patterns.strategy;


public class DealContext {
    private String type;
    private DealStrategy deal;
    public  DealContext(String type,DealStrategy deal){
        this.type = type;
        this.deal = deal;
    }
    public DealStrategy getDeal(){
        return deal;
    }

    public boolean options(String type){
        return this.type.equals(type);
    }
}
