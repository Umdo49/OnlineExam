package com.oExam.onlineExam.service;
import com.oExam.onlineExam.dto.QuestionDTO;
import com.oExam.onlineExam.model.Question;
import com.oExam.onlineExam.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    // Soru ekleme
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Soru güncelleme
    public Question updateQuestion(Long id, Question questionDetails) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question existingQuestion = questionOptional.get();
            existingQuestion.setQuestionText(questionDetails.getQuestionText());
            existingQuestion.setOptionA(questionDetails.getOptionA());
            existingQuestion.setOptionB(questionDetails.getOptionB());
            existingQuestion.setOptionC(questionDetails.getOptionC());
            existingQuestion.setOptionD(questionDetails.getOptionD());
            existingQuestion.setCorrectAnswer(questionDetails.getCorrectAnswer());
            existingQuestion.setQuestionType(questionDetails.getQuestionType());
            return questionRepository.save(existingQuestion);
        }
        return null;
    }

    // Soru silme
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    // Sınav ID'sine göre soruları listeleme
    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }
}
