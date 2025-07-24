package com.enotes.endpoint;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/files")
public interface FileViewControllerEndpoint {

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) throws Exception;
}
