package com.crop.goodcrop.like;

import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class LikeTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void sampleData() {
        ArrayList<Member> members = new ArrayList<>();
        for(int idx = 0; idx < 50; idx++) {
            String name = RandomStringUtils.randomAlphanumeric(10);
            Member member = Member.builder()
                    .nickname(name)
                    .email(name + "@email.com")
                    .password("1234")
                    .birth(LocalDate.now())
                    .build();
            members.add(member);
        }
        memberRepository.saveAll(members);

        ArrayList<Product> products = new ArrayList<>();
        for(Member member : members) {
            for(int idx = 0; idx < 10; idx++) {
                String name = RandomStringUtils.randomAlphanumeric(10);
                Product product = Product.builder()
                        .name(name)
                        .price(5000)
                        .build();
                products.add(product);
            }
        }
        productRepository.saveAll(products);
    }
}
