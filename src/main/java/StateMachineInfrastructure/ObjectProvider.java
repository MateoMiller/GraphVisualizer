package StateMachineInfrastructure;

public class ObjectProvider<T> {
    private T object;

    public ObjectProvider(T object){
        this.object = object;
    }

    public void setObject(T object){
        this.object = object;
    }

    public T getObject(){
        return object;
    }
}
