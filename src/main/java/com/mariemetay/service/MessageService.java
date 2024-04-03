package com.mariemetay.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mariemetay.model.Message;
import com.mariemetay.model.dto.MessageDTO;
import com.mariemetay.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public Message postMessage(MessageDTO messageDTO) {
        Message message = dtoToEntity(messageDTO);
        message.setRentalId(messageDTO.getRental_id());
        message.setUserId(messageDTO.getUser_id());
        return messageRepository.save(message);
    }

    private Message dtoToEntity(MessageDTO messageDTO) {
        return modelMapper.map(messageDTO, Message.class);
    }

}
