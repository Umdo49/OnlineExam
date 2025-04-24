package com.oExam.onlineExam.service;

import com.oExam.onlineExam.dto.ExamDTO;
import com.oExam.onlineExam.model.*;
import com.oExam.onlineExam.repository.ExamRepository;
import com.oExam.onlineExam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    // Sınav oluşturma
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    // Kullanıcıya ait sınavları listeleme
    public List<Exam> getExamsByCreator(Long creatorId) {
        return examRepository.findByCreatorId(creatorId);
    }

    // Yayınlanmış sınavı almak
    public Optional<Exam> getPublishedExam(Long examId) {
        return examRepository.findByIdAndIsPublishedTrue(examId);
    }

    // Yayınlanmış sınavların listesini almak
    public List<Exam> getPublishedExams() {
        return examRepository.findByIsPublishedTrue();
    }

    // Sınavı yayınlama
    public Exam publishExam(Long examId) {
        Optional<Exam> examOpt = examRepository.findById(examId);
        if (examOpt.isPresent()) {
            Exam exam = examOpt.get();
            exam.setPublished(true);
            return examRepository.save(exam);
        }
        return null;
    }

    // Sınav süresi dolmuş mu?
    public boolean isExamExpired(Long examId) {
        Optional<Exam> examOpt = examRepository.findById(examId);
        return examOpt.map(exam -> exam.getEndTime().isBefore(LocalDateTime.now())).orElse(false);
    }

    // Katılımcı sayısını güncelleme
    public void updateParticipation(Long examId) {
        Optional<Exam> examOpt = examRepository.findById(examId);
        examOpt.ifPresent(exam -> {
            exam.setParticipations(exam.getParticipations()); // Katılımcıları güncelle
            examRepository.save(exam);
        });
    }
}

