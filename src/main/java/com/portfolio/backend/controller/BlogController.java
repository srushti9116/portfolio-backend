package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Blog;
import com.portfolio.backend.repository.BlogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final BlogRepository blogRepository;

    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        return blogRepository.save(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @RequestBody Blog blog) {

        return blogRepository.findById(id)
                .map(existingBlog -> {

                    existingBlog.setTitle(blog.getTitle());
                    existingBlog.setSlug(blog.getSlug());
                    existingBlog.setContent(blog.getContent());
                    existingBlog.setExcerpt(blog.getExcerpt());
                    existingBlog.setCoverImage(blog.getCoverImage());
                    existingBlog.setCategory(blog.getCategory());
                    existingBlog.setTags(blog.getTags());
                    existingBlog.setAuthor(blog.getAuthor());
                    existingBlog.setPublished(blog.getPublished());
                    existingBlog.setPublishedAt(blog.getPublishedAt());

                    return ResponseEntity.ok(
                            blogRepository.save(existingBlog)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id) {

        if (!blogRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        blogRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}