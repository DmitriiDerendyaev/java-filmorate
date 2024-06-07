    package ru.yandex.practicum.filmorate.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.model.Mpa;
    import ru.yandex.practicum.filmorate.service.MpaService;

    import java.util.List;

    @Slf4j
    @RestController
    @RequestMapping("/mpa")
    @RequiredArgsConstructor
    @CrossOrigin(origins = "http://localhost:8081")
    public class MpaController {

        private final MpaService mpaService;

        @GetMapping
        public List<Mpa> getAllMpa() {
            final List<Mpa> mpa = mpaService.getMpa();
            log.info("Get all genres - {}", mpa);
            return mpa;
        }

        @GetMapping("/{id}")
        public Mpa getMpaById(@PathVariable Long id) {
            return mpaService.getMpaById(id);
        }
    }
