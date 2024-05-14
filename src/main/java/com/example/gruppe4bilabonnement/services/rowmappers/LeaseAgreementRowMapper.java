package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseAgreementRowMapper implements RowMapper<LeaseAgreement> {
    @Override
    public LeaseAgreement mapRow(ResultSet rs, int rowNum) throws SQLException {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setId(rs.getInt("id"));
        leaseAgreement.setCustomerId(rs.getInt("customer_id"));
        leaseAgreement.setCarId(rs.getInt("car_id"));
        leaseAgreement.setStartDate(rs.getDate("start_date"));
        leaseAgreement.setEndDate(rs.getDate("end_date"));
        leaseAgreement.setBoughtKm(rs.getLong("bought_km"));
        leaseAgreement.setStartKm(rs.getLong("start_km"));
        leaseAgreement.setPrice(rs.getDouble("price"));
        leaseAgreement.setPaymentTime(rs.getInt("payment_time"));
        leaseAgreement.setTransportTime(rs.getInt("transport_time"));
        return leaseAgreement;
    }
}
