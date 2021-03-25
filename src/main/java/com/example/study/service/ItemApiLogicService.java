package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {


    PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        //TODO : null일 때 예외처리

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);
        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(item -> response(item))
                .orElseGet(() -> {
                    return Header.ERROR("데이터 없음");
                });
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> req) {

        ItemApiRequest body = req.getData();

        baseRepository.findById(body.getId())
                .map(entityItem -> {

                    entityItem.setStatus(body.getStatus())
                            .setName(body.getName())
                            .setTitle(body.getTitle())
                            .setContent(body.getContent())
                            .setPrice(body.getPrice())
                            .setBrandName(body.getBrandName())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt());
                    return entityItem;
                })
                .map(newEntityItem->{
                    baseRepository.save(newEntityItem);
                    return newEntityItem;
                })
                .map(item -> response(item))
                .orElseGet(()-> Header.ERROR("데이터 없음"));
        return null;
    }

    // 6, 10, 13, 9, 8, 1

    /*
    * 1. oox, oxo, xoo 의 경우가 존재한다.
 1-2. oox 의 경우 dp[i-1] 가 된다.

 1-3. oxo 의 경우 dp[i-2] + arr[i] 가 된다.

 1-4. xoo 의 경우 do[i-3] + arr[i-1] + arr[i] 가 된다.

2. 위의 3경우중 가장 큰 값을 선택하면 된다.

 dp[i] = Math.max(dp[i - 1], Math.max(dp[i - 2] + arr[i], dp[i - 3] + arr[i - 1] + arr[i])) 라는 수식이 나온다.

3. 끝.

4. 아 여기서 N=1 일때와 2일때가 존재한다. 이때의 경우를 조건을 통해 잘 계산하도록 한다.


    * */


    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(item -> {
                    baseRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이텉 없음"));
    }

    private Header<ItemApiResponse> response(Item item){

        //어떤 것을 보여줄것인가?
        String statusTitle = item.getStatus().getTitle();
        String statusDescription = item.getStatus().getDescription();


        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(body);
    }

}
