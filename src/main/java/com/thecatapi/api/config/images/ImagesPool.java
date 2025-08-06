package com.thecatapi.api.config.images;

import com.thecatapi.api.models.responses.image.OwnedImageResponseDto;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

public class ImagesPool {

    private final BlockingQueue<OwnedImageResponseDto> available;

    public ImagesPool(ImagesConfig config) {
        this.available = new LinkedBlockingQueue<>(config.getImages());
    }

    public OwnedImageResponseDto borrowImage() {
        try {
            return available.take();
        } catch (InterruptedException e) {
            currentThread().interrupt();
            throw new RuntimeException("Interrupted while acquiring Book", e);
        }
    }

    public void returnImage(OwnedImageResponseDto image) {
        requireNonNull(image);
        available.offer(image);
    }

    public List<OwnedImageResponseDto> borrowAllAvailableImages() {
        var availableBooks = available
                .stream()
                .toList();

        available.removeAll(availableBooks);

        return availableBooks;
    }

    public void returnImages(List<OwnedImageResponseDto> images) {
        requireNonNull(images);
        images.forEach(this::returnImage);
    }
}
