package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Rate;

@Transactional
@Repository
public interface IRateDAO extends JpaRepository<Rate, Integer> {

	List<Rate> getRateByFoodId(int foodId);
}
