package com.example.barbershop.Interface;

import com.example.barbershop.Model.Salon;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSuccess(List<Salon> SalonList );
    void onBranchFailed(String message);

}
