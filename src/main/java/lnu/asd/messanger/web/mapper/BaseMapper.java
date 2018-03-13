package lnu.asd.messanger.web.mapper;

public interface BaseMapper<F, T> {

    T map(F entity);
}
