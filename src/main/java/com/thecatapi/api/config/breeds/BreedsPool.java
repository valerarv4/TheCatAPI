package com.thecatapi.api.config.breeds;

import com.thecatapi.api.models.responses.image.BreedResponseDto;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

public class BreedsPool {

    private final BlockingQueue<BreedResponseDto> available;

    public BreedsPool(BreedsConfig config) {
        this.available = new LinkedBlockingQueue<>(config.getBreeds());
    }

    public BreedResponseDto borrowImage() {
        try {
            return available.take();
        } catch (InterruptedException e) {
            currentThread().interrupt();
            throw new RuntimeException("Interrupted while acquiring Book", e);
        }
    }

    public void returnBreed(BreedResponseDto breed) {
        requireNonNull(breed);
        available.offer(breed);
    }

    public List<BreedResponseDto> borrowAllAvailableBreeds() {
        var availableBooks = available
                .stream()
                .toList();

        available.removeAll(availableBooks);

        return availableBooks;
    }

    public void returnBreeds(List<BreedResponseDto> breeds) {
        requireNonNull(breeds);
        breeds.forEach(this::returnBreed);
    }
}
