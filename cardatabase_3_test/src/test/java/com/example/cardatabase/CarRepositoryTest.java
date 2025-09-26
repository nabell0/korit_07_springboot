package com.example.cardatabase;


import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("차량 저장 메서드 테스트")
    void saveCar(){
        // given - 제반 준비 사항
        // Car Entity를 확인해봤을 때 field로 Owner를 요구하기 때문에
        // 얘부터 먼저 만들고 -> ownerRepository에 저장
        Owner owner = new Owner("Gemini","GPT");
        ownerRepository.save(owner);
        // 그리고 Car 객체를 만들겁니다.
        Car car = new Car("Ford","Mustang","Red","ABCEDF",2021,567890,owner);

        // when - 테스트 실행
        // 저장이 됐는가를 확인하기 위한 부분
        carRepository.save(car);
        // then - 그 결과가 어떠할지
        assertThat(carRepository.findById(car.getId())).isPresent();    // 이건 그냥 결과값이 하나일테니까.

        assertThat(carRepository.findById(car.getId()).get().getBrand()).isEqualTo("Ford");

    }
    @Test
    @DisplayName("삭제 테스트 : ")
    void deleteCar(){
        Owner owner2 = new Owner("Gemin","GPT1");
        ownerRepository.save(owner2);
        Car car2 = new Car("Ford2","Mustang2","white","ABCEDFG",2022,567891,owner2);

        carRepository.save(car2);
// when 삭제
        carRepository.deleteAll();
//        carRepository.deleteById(car2.getId());
        // then -> 삭제가 올바로 되었는지 검증
        assertThat(carRepository.count()).isEqualTo(0);
//        assertThat(carRepository.count()).isEqualTo(3); -> deleteById() 를 쓸 경우
    }
    @Test
    @DisplayName("특정 브랜드 조회 :")
    void findByBrandShouldReturnCar(){
        Owner owner = new Owner("Gemini","GPT");
        ownerRepository.save(owner);

        Car car = new Car("Ford","Mustang","Red","ABCEDF",2021,567890,owner);
        carRepository.save(car);
        Car car2 = new Car("Ford","Mustang1","Red","ABCEDF",2022,567890,owner);
        carRepository.save(car2);
        Car car3 = new Car("Ford2","Mustang","Red","ABCEDF",2025,567890,owner);
        carRepository.save(car3);
        List<Car> Ford = carRepository.findByBrand("Ford");
        // then에서의 검증은 List 내부의 element의 자료형이 car 객체일테니까,
        //그 객체의 getBrand()의 결과값이 우리가 findByBrand()의 arugment로 쓴 값과 동일한지 체크 가능.

//        assertThat(Ford.get(0).getBrand()).isEqualTo("Ford");
//        혹은, 현재 저희가 Ford 차량을 두대 만들었니까 size()의 결과값이 2
        assertThat(Ford.size()).isEqualTo(2);
    }
}
