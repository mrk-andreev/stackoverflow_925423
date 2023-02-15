package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class StringBenchmark {
    private String foo;

    private String bar;

    @Setup(value = Level.Iteration)
    public void setup() {
        foo = UUID.randomUUID().toString();
        bar = UUID.randomUUID().toString();
    }

    @Benchmark
    public void concat(Blackhole blackhole) {
        blackhole.consume(foo + "_" + bar);
    }

    @Benchmark
    public void fmt(Blackhole blackhole) {
        blackhole.consume(String.format("%s_%s", foo, bar));
    }
}
