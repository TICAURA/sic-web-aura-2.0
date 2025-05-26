package mx.com.consolida.converter;

/**
 * @author Abel
 */
public class BuilderObjects<E, D> {

    private E entity;
    private D dto;
    private STATE state;
    

    public enum STATE {

        INIT, PROCESS, CONVERTED, SLEEP;

        private STATE() {
        }
    }

    public BuilderObjects(E entity, D dto) {
        this.entity = entity;
        this.dto = dto;
        this.state = STATE.INIT;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public D getDto() {
        return dto;
    }

    public void setDto(D dto) {
        this.dto = dto;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }
}
