package com.example.study.ifs;

import com.example.study.model.network.Header;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header <Req> request);        //todo request object 추ㅏ
    Header<Res> read(Long id);
    Header<Res> update(Header <Req> req);
    Header<Res> delete(Long id);
}
