# Benchmark

```
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
```



# Results

```
Benchmark               Mode  Cnt    Score   Error  Units
StringBenchmark.concat  avgt   25   15.112 ± 0.194  ns/op
StringBenchmark.fmt     avgt   25  143.320 ± 0.787  ns/op
```


```
Result "org.example.StringBenchmark.concat":
  15.112 ±(99.9%) 0.194 ns/op [Average]
  (min, avg, max) = (14.618, 15.112, 15.586), stdev = 0.259
  CI (99.9%): [14.917, 15.306] (assumes normal distribution)

Result "org.example.StringBenchmark.fmt":
  143.320 ±(99.9%) 0.787 ns/op [Average]
  (min, avg, max) = (141.255, 143.320, 145.262), stdev = 1.051
  CI (99.9%): [142.532, 144.107] (assumes normal distribution)

```

```
# Run complete. Total time: 00:16:45

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
```
