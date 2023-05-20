package com.resource.service.service.chat;

import java.util.List;

public interface ChatServiceInterface<C, ID>{
    List<C> findAllByUserId(ID userId);
    C save(C entity);

    C update(ID idMessage, C entity);
    void delete(ID id);
}
