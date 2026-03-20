import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CvService } from './cv.service';
import { AuthService } from '../../core/auth/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [ReactiveFormsModule, MatInputModule, MatButtonModule],
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.css']
})
export class CvComponent implements OnInit {

  studentId!: number;

  form = this.fb.group({
    photoUrl: [''],
    summary: [''],

    educations: this.fb.array<FormGroup>([]),
    experiences: this.fb.array<FormGroup>([]),
    skills: this.fb.array<FormGroup>([])
  });

  constructor(
    private fb: FormBuilder,
    private cvService: CvService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    const user = this.authService.getUser();
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }
    this.studentId = user.id;
    this.loadCv();
  }

  // ===== GETTERS =====
  get educations(): FormArray<FormGroup> {
    return this.form.get('educations') as FormArray<FormGroup>;
  }

  get experiences(): FormArray<FormGroup> {
    return this.form.get('experiences') as FormArray<FormGroup>;
  }

  get skills(): FormArray<FormGroup> {
    return this.form.get('skills') as FormArray<FormGroup>;
  }

  // ===== LOAD CV =====
  loadCv() {
    this.cvService.getCv(this.studentId).subscribe({
      next: (cv) => {
        this.clearArrays();

        if (!cv || cv.educations.length === 0) {
          this.addEducation();
          this.addExperience();
          this.addSkill();
        } else {
          this.form.patchValue({
            photoUrl: cv.photoUrl,
            summary: cv.summary
          });

          cv.educations.forEach(e => this.educations.push(this.fb.group({
            institution: [e.institution],
            degree: [e.degree]
          }) as FormGroup));

          cv.experiences.forEach(e => this.experiences.push(this.fb.group({
            companyName: [e.companyName],
            position: [e.position]
          }) as FormGroup));

          cv.skills.forEach(s => this.skills.push(this.fb.group({
            skillName: [s.skillName]
          }) as FormGroup));
        }
      },
      error: () => {
        this.addEducation();
        this.addExperience();
        this.addSkill();
      }
    });
  }

  clearArrays() {
    this.educations.clear();
    this.experiences.clear();
    this.skills.clear();
  }

  // ===== ADD METHODS =====
  addEducation() {
    this.educations.push(this.fb.group({
      institution: [''],
      degree: ['']
    }) as FormGroup);
  }

  addExperience() {
    this.experiences.push(this.fb.group({
      companyName: [''],
      position: ['']
    }) as FormGroup);
  }

  addSkill() {
    this.skills.push(this.fb.group({
      skillName: ['']
    }) as FormGroup);
  }

  // ===== SAVE =====
  save() {
    const body = {
      photoUrl: this.form.value.photoUrl,
      summary: this.form.value.summary,
      newEducations: this.form.value.educations,
      newExperiences: this.form.value.experiences,
      newSkills: this.form.value.skills
    };

    this.cvService.createCv(this.studentId, body).subscribe({
      next: () => alert('Saved!'),
      error: () => alert('Error saving CV')
    });
  }

  // ===== DOWNLOAD PDF =====
  downloadPdf() {
    this.cvService.downloadPdf(this.studentId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }
}