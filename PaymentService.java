package com.oExam.onlineExam.service;



import com.oExam.onlineExam.dto.PaymentRequest;
import com.oExam.onlineExam.dto.PaymentResponse;
import com.oExam.onlineExam.model.Exam;
import com.oExam.onlineExam.model.Payment;
import com.oExam.onlineExam.model.User;
import com.oExam.onlineExam.repository.ExamRepository;
import com.oExam.onlineExam.repository.PaymentRepository;
import com.oExam.onlineExam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    // Ödeme işlemini kaydetme
    public Payment processPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now()); // Ödeme tarihi
        payment.setStatus("Pending"); // Başlangıç durumu "Bekliyor"
        return paymentRepository.save(payment);
    }

    // Kullanıcıya ait ödemeleri alma
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    // Sınav id'sine göre ödemeleri alma
    public List<Payment> getPaymentsByExamId(Long examId) {
        return paymentRepository.findByExamId(examId);
    }

    // Ödeme durumu güncelleme
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status); // Durum güncellemesi (Ödendi, İptal Edildi, vb.)
            return paymentRepository.save(payment);
        }
        return null;
    }
}

