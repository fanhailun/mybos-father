package cn.itcast.bos.dao.qp;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.qp.WorkBill;

public interface WorkBillDao extends JpaRepository<WorkBill, String> {

}
