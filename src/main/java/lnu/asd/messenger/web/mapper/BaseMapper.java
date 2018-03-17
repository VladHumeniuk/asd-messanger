package lnu.asd.messenger.web.mapper;

public interface BaseMapper<F, T> {

    T map(F entity);
}
