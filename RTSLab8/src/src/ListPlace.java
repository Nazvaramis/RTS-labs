import Interfaces.PlaceTemplate;

import java.util.List;

public class ListPlace implements PlaceTemplate {

    List<relations> value;
    String name;

    public ListPlace(String name, List<relations> value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String GetPlaceName() {
        return this.name;
    }

    @Override
    public void SetPlaceName(String name) {
        this.name=name;
    }
    @Override
    public Object Get() {
        return this.value;
    }

    @Override
    public Boolean IsNull() {
        return this.Get() == null;
    }

    @Override
    public void Set(Object value) {
        this.value= (List) value;
    }

    @Override
    public String Print() {
        return "["+this.value+"]";
    }

    @Override
    public void Init(String name, Object value) {
        this.SetPlaceName(name);
        this.Set(value);
    }


}